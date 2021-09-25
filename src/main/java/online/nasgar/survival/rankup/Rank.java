package online.nasgar.survival.rankup;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter @AllArgsConstructor
public class Rank {

    private final String name, prefix;
    private final int time;
    private final List<String> commands;

}
