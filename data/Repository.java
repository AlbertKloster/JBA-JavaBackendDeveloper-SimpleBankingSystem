package banking.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {

    private static Repository instance;
    private String dbPath;
    private Repository() { }

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    private static final String driver = "jdbc:sqlite:";

    public void init(String dbPath) {
        this.dbPath = dbPath;
        String url = driver + dbPath;
        String sql = "CREATE TABLE IF NOT EXISTS card ("
                + "	id integer PRIMARY KEY AUTOINCREMENT,"
                + "	number TEXT NOT NULL,"
                + "	pin TEXT NOT NULL,"
                + "	balance INTEGER DEFAULT 0"
                + ")";

        try (Connection conn = DriverManager.getConnection(url);
             Statement statement = conn.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getBalance(String number) {
        String url = driver + dbPath;
        String sql = "SELECT balance FROM card " +
                "WHERE number = " + number + ";";

        try (Connection conn = DriverManager.getConnection(url);
             Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            int balance = 0;
            while (resultSet.next()) {
                balance = resultSet.getInt("balance");
            }
            return String.valueOf(balance);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String getPin(String number) {
        String url = driver + dbPath;
        String sql = "SELECT pin FROM card " +
                "WHERE number = " + number + ";";

        try (Connection conn = DriverManager.getConnection(url);
             Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            int balance = 0;
            while (resultSet.next()) {
                balance = resultSet.getInt("pin");
            }
            return String.valueOf(balance);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "0";
        }
    }

    public List<String> getAllCardNumbers() {
        String url = driver + dbPath;
        List<String> cardNumbers = new ArrayList<>();
        String sql = "SELECT * FROM card";
        try (Connection conn = DriverManager.getConnection(url);
             Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                cardNumbers.add(resultSet.getString("number"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cardNumbers;
    }

    public List<String> getAllPins() {
        String url = driver + dbPath;
        List<String> cardNumbers = new ArrayList<>();
        String sql = "SELECT * FROM card";
        try (Connection conn = DriverManager.getConnection(url);
             Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                cardNumbers.add(resultSet.getString("pin"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cardNumbers;
    }

    public void save(Account account) {
        String url = driver + dbPath;
        String sql = "INSERT INTO card (number, pin, balance) VALUES(?,?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, account.getCardNumber());
            preparedStatement.setString(2, account.getPin());
            preparedStatement.setInt(3, Integer.parseInt("0"));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(String cardNumber) {
        String url = driver + dbPath;
        String sql = "DELETE FROM card WHERE number = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, cardNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addIncome(int incomeToAdd, String number) {
        String url = driver + dbPath;

        String balance = getBalance(number);
        if (balance == null)
            return;

        String sql = "UPDATE card SET balance = ? WHERE number = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, Integer.parseInt(balance) + incomeToAdd);
            preparedStatement.setString(2, number);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void transfer(String numberCreditor, String numberBeneficiary, String moneyToTransfer) {
        String url = driver + dbPath;

        String balanceCreditor = getBalance(numberCreditor);
        if (balanceCreditor == null)
            return;

        String balanceBeneficiary = getBalance(numberBeneficiary);
        if (balanceBeneficiary == null)
            return;

        String sql = "UPDATE card SET balance = ? WHERE number = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            // Disable auto-commit mode
            conn.setAutoCommit(false);

            preparedStatement.setInt(1, Integer.parseInt(balanceCreditor) - Integer.parseInt(moneyToTransfer));
            preparedStatement.setString(2, numberCreditor);
            preparedStatement.executeUpdate();

            preparedStatement.setInt(1, Integer.parseInt(balanceBeneficiary) + Integer.parseInt(moneyToTransfer));
            preparedStatement.setString(2, numberBeneficiary);
            preparedStatement.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
