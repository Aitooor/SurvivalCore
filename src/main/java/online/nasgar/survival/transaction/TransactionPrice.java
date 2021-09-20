package online.nasgar.survival.transaction;

import java.util.List;

public interface TransactionPrice {

    String getName();

    int getPrice();

    List<String> getCommands();

}
