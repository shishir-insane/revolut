# Revolut Test #

I have used Spark for Java to create a really simple service.  

The project packages via maven as an executable standalone jar with com.sk.revolut.Application as the main class.  It will start a server on localhost:4567. 

It exposes 2 endpoints which can be used to transfer funds and check balances:

1. /accounts/transfer/{amount}/from/{id}/to/{id2}
2. /accounts/balance/{id}

The in-memory DAO is prepopulated with 2 accounts, one with id 123, and the other with id 456.