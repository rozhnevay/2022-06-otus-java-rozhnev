## Результат работы DbServiceDemo - после включения кэша, запрос в БД улетел только один раз, остальное - из кэша

```shell
Hibernate: select client0_.id as id1_0_0_, client0_.name as name2_0_0_ from client client0_ where client0_.id=?
2022-09-26_21:23:32.386 INFO  r.o.crm.service.DbServiceClientImpl - client: Optional[ru.otus.crm.model.Client@4d09cade]
Hibernate: select client0_.id as id1_0_0_, client0_.name as name2_0_0_ from client client0_ where client0_.id=?
2022-09-26_21:23:33.404 INFO  r.o.crm.service.DbServiceClientImpl - client: Optional[ru.otus.crm.model.Client@ea04cab]
Hibernate: select client0_.id as id1_0_0_, client0_.name as name2_0_0_ from client client0_ where client0_.id=?
2022-09-26_21:23:34.420 INFO  r.o.crm.service.DbServiceClientImpl - client: Optional[ru.otus.crm.model.Client@6a32191e]
2022-09-26_21:23:34.424 INFO  ru.otus.demo.DbServiceDemo - ====================
2022-09-26_21:23:34.440 INFO  org.ehcache.core.EhcacheManager - Cache 'Cache' created in EhcacheManager.
Hibernate: select client0_.id as id1_0_0_, client0_.name as name2_0_0_ from client client0_ where client0_.id=?
2022-09-26_21:23:35.448 INFO  r.o.crm.service.DbServiceClientImpl - client: Optional[ru.otus.crm.model.Client@68ab6ab0]
2022-09-26_21:23:35.454 INFO  r.o.crm.service.DbServiceClientImpl - client: Optional[ru.otus.crm.model.Client@68ab6ab0]
2022-09-26_21:23:35.455 INFO  r.o.crm.service.DbServiceClientImpl - client: Optional[ru.otus.crm.model.Client@68ab6ab0]
```