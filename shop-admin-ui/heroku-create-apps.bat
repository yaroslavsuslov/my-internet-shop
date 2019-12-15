heroku apps:create shop-admin-ui
heroku addons:create heroku-postgresql:hobby-dev --app shop-admin-ui
heroku config:set JDBC_DRIVER_CLASS=org.postgresql.Driver HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect --app shop-admin-ui