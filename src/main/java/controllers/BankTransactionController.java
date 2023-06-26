package controllers;

import models.BankTransaction;
import models.Card;
import models.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import spark.Request;
import spark.Response;
import spark.Route;
import util.HibernateUtil;

import java.util.Collections;
import java.util.List;

// daos = repositories
// cl
// services qual something else which is the business
// logic
public class BankTransactionController {

    public static Route getAllBankTransactionsRoute = (Request request, Response response) -> {
        List<BankTransaction> bankTransactions = getAllBankTransactions();
        StringBuilder sb = new StringBuilder();
        for (BankTransaction bankTransaction : bankTransactions) {
            sb.append(bankTransaction.toString()).append("\n");
        }
        return sb.toString();
    };

    public static Route getBankTransactionByIdRoute = (Request request, Response response) -> {
        int id = Integer.parseInt(request.params("id"));
        BankTransaction bankTransaction = getBankTransactionById(id);
        if (bankTransaction != null) {
            return bankTransaction.toString();
        } else {
            response.status(404);
            return "BankTransaction not found";
        }
    };

    public static Route createBankTransactionRoute = (Request request, Response response) -> {
        String amount = request.queryParams("amount");
        String date = request.queryParams("date");

        // Create related objects
        User user = new User("John Doe");
        Card card = new Card("123456789", "Visa");
//        Product product = new Product("123", "Product 1");
//
//        // Create BankTransaction with related objects
//        BankTransaction bankTransaction = new BankTransaction(Double.parseDouble(amount), date, user, card, product);
//        createBankTransaction(bankTransaction);
//
//        response.status(201);
        return "dashboard";
    };

    public static Route updateBankTransactionRoute = (Request request, Response response) -> {
        int id = Integer.parseInt(request.params("id"));
        BankTransaction bankTransaction = getBankTransactionById(id);
        if (bankTransaction != null) {
            String amount = request.queryParams("amount");
            String date = request.queryParams("date");

            bankTransaction.setAmount(Double.parseDouble(amount));
            bankTransaction.setDate(date);

            updateBankTransaction(bankTransaction);

            return "dashboard";
        } else {
            response.status(404);
            return "BankTransaction not found";
        }
    };

    public static Route deleteBankTransactionRoute = (Request request, Response response) -> {
        int id = Integer.parseInt(request.params("id"));
        BankTransaction bankTransaction = getBankTransactionById(id);
        if (bankTransaction != null) {
            deleteBankTransaction(bankTransaction);
            return "dashboard";
        } else {
            response.status(404);
            return "BankTransaction not found";
        }
    };

    public static List<BankTransaction> getAllBankTransactions() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<BankTransaction> query = session.createQuery("FROM BankTransaction", BankTransaction.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static BankTransaction getBankTransactionById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<BankTransaction> query = session.createQuery("FROM BankTransaction WHERE id = :id", BankTransaction.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void createBankTransaction(BankTransaction bankTransaction) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(bankTransaction);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateBankTransaction(BankTransaction bankTransaction) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(bankTransaction);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteBankTransaction(BankTransaction bankTransaction) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(bankTransaction);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
