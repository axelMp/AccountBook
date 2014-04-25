dataSource {

    pooled = true
    jmxExport = true
    dbCreate = "update"
    url = "jdbc:postgresql://localhost:5432/accountbook"
    driverClassName = "org.postgresql.Driver"
    dialect = "org.hibernate.dialect.PostgreSQLDialect"
    username = "pi"
    password = "raspberry"
}

hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
}

// environment specific settings
// environments {
//     development {
//         dataSource {
//             dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
//             url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
//         }
//     }
//     test {
//         dataSource {
//             dbCreate = "update"
//             url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
//         }
//     }
//}
