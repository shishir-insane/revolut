package com.sk.revolut;

import static spark.Spark.get;

import org.apache.log4j.Logger;

import com.sk.revolut.dao.AccountDaoInMemoryImpl;
import com.sk.revolut.domain.UserAccount;
import com.sk.revolut.exception.BalanceException;
import com.sk.revolut.service.AccountService;
import com.sk.revolut.service.AccountServiceImpl;

/**
 * The Class Application.
 */
public class Application {
    private static final Logger LOG = Logger.getLogger(Application.class);

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
        final AccountDaoInMemoryImpl accountDao = new AccountDaoInMemoryImpl();
        accountDao.addOrUpdate("123", new UserAccount("123", 100));
        accountDao.addOrUpdate("456", new UserAccount("456", 100));

        final AccountService accountService = new AccountServiceImpl(accountDao);

        get("/accounts/balance/*", (req, res) -> {
            try {
                if (req.splat().length != 1) {
                    throw new IllegalStateException("Request should look like /accounts/balance/<id>");
                }
                final String accountId = req.splat()[0];

                return "Balance of " + accountId + "=" + accountService.getBalance(accountId);
            } catch (final IllegalArgumentException e) {
                LOG.error("IllegalArgumentException", e);
                return "ERROR: " + e;
            } catch (final IllegalStateException e) {
                LOG.error("IllegalStateException", e);
                return "ERROR: " + e;
            } catch (final Throwable e) {
                LOG.error("Unexpected exception", e);
                return "Unable to process request, unexpected error";
            }
        }

        );

        get("/accounts/transfer/*/from/*/to/*", (req, res) -> {
            try {
                if (req.splat().length != 3) {
                    throw new IllegalStateException(
                            "Request should look like /accounts/transfer/<qty>/from/<id1>/to/<id2>");
                }
                final double quantity = Double.valueOf(req.splat()[0]);
                final String fromAccount = String.valueOf(req.splat()[1]);
                final String toAccount = String.valueOf(req.splat()[2]);
                accountService.transfer(fromAccount, toAccount, quantity);
                return "Transfer from " + fromAccount + " to " + toAccount + " of " + quantity + " was successful";
            } catch (final NumberFormatException e) {
                LOG.error("NumberFormatException", e);
                return "Invalid quantity passed to service";
            } catch (final BalanceException e) {
                LOG.error("Balance exception", e);
                return "ERROR: " + e.getMessage();
            } catch (final IllegalStateException e) {
                LOG.error("IllegalStateException", e);
                return "ERROR: " + e.getMessage();
            } catch (final IllegalArgumentException e) {
                LOG.error("IllegalArgumentException", e);
                return "ERROR: " + e.getMessage();
            } catch (final Throwable e) {
                LOG.error("Unexpected exception", e);
                return "Unexpected error, unable to process request";
            }
        });
    }
}