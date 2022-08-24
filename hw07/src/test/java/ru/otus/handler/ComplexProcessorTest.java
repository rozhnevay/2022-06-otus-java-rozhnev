package ru.otus.handler;


import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.exceptions.EvenSecondException;
import ru.otus.model.Message;
import ru.otus.listener.Listener;
import ru.otus.model.MessageWithTime;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.Processor;

import java.util.ArrayList;
import java.util.List;
import ru.otus.processor.ProcessorEvenSecondException;
import ru.otus.processor.ProcessorSwapFields11And12;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ComplexProcessorTest {

    @Test
    @DisplayName("Тестируем вызовы процессоров")
    void handleProcessorsTest() {
        //given
        var message = Message.builder().id(1L).field7("field7").build();

        var processor1 = mock(Processor.class);
        when(processor1.process(message)).thenReturn(message);

        var processor2 = mock(Processor.class);
        when(processor2.process(message)).thenReturn(message);

        var processors = List.of(processor1, processor2);

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
        });

        //when
        var result = complexProcessor.handle(message);

        //then
        verify(processor1).process(message);
        verify(processor2).process(message);
        assertThat(result).isEqualTo(message);
    }

    @Test
    @DisplayName("Тестируем обработку исключения")
    void handleExceptionTest() {
        //given
        var message = Message.builder().id(1L).field8("field8").build();

        var processor1 = mock(Processor.class);
        when(processor1.process(message)).thenThrow(new RuntimeException("Test Exception"));

        var processor2 = mock(Processor.class);
        when(processor2.process(message)).thenReturn(message);

        var processors = List.of(processor1, processor2);

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
            throw new TestException(ex.getMessage());
        });

        //when
        assertThatExceptionOfType(TestException.class).isThrownBy(() -> complexProcessor.handle(message));

        //then
        verify(processor1, times(1)).process(message);
        verify(processor2, never()).process(message);
    }

    @Test
    @DisplayName("Тестируем обработку замены значения филдов")
    void handleSwapProcessorTest() {
        //given
        var ofm11 = new ObjectForMessage();
        String ofm11Name = "ofm11";
        ofm11.setData(List.of(ofm11Name));

        var ofm12 = new ObjectForMessage();
        String ofm12Name = "ofm12";
        ofm12.setData(List.of(ofm12Name));

        var message = Message.builder().id(1L).field11(ofm11).field12(ofm12).build();
        var processor = new ProcessorSwapFields11And12();
        //when
        message = processor.process(message);
        assertThat(message.getField11().getData().get(0)).isEqualTo(ofm12Name);
        assertThat(message.getField12().getData().get(0)).isEqualTo(ofm11Name);
    }

    @Test
    @DisplayName("Тестируем подъем исключения каждую четную секунду")
    void handleThrowEvenSecondExceptionTest() throws InterruptedException {
        //given
        var processor = new ProcessorEvenSecondException();
        //when
        for (var i = 0; i < 10; i++) {
            var now = LocalDateTime.now();
            var message = MessageWithTime.builder().createdAt(now).build();
            if (now.getSecond() % 2 == 0) {
                assertThatExceptionOfType(EvenSecondException.class).isThrownBy(() -> processor.process(message));
            }
            Thread.sleep(1000);
        }
    }

    @Test
    @DisplayName("Тестируем уведомления")
    void notifyTest() {
        //given
        var message = Message.builder().id(1L).field9("field9").build();

        var listener = mock(Listener.class);

        var complexProcessor = new ComplexProcessor(new ArrayList<>(), (ex) -> {
        });

        complexProcessor.addListener(listener);

        //when
        complexProcessor.handle(message);
        complexProcessor.removeListener(listener);
        complexProcessor.handle(message);

        //then
        verify(listener, times(1)).onUpdated(message);
    }

    private static class TestException extends RuntimeException {
        public TestException(String message) {
            super(message);
        }
    }
}