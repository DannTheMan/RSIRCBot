package org.nolat.rsircbot.commands;

import java.io.IOException;

import org.nolat.rsircbot.RSIRCBot;
import org.nolat.rsircbot.data.HiscoreData;
import org.nolat.rsircbot.tools.Names;

public class CombatCommand extends Command {

    public CombatCommand() {
        super("combat");
        addAlternativeCommand("cmb");
        setArgString("<username>");
        setHelpMessage("Calculates the user's combat level");
    }

    @Override
    public void executeCommand(RSIRCBot bot, String channel, String executor, String message) {
        String[] args = message.split(" "); //0 is the command
        if (args.length != 2) {
            bot.sendMessage(channel, executor, getUsageString(), this);
        } else {
            String username = Names.processUsername(bot, args[1], executor);

            try {
                HiscoreData hiscores = new HiscoreData(username);
                bot.sendMessage(channel, executor, String.format(
                        "%s is combat level %.3f (Atk: %d; Str: %d; Def: %d; HP: %d; Prayer: %d; Ranged: %d; Magic: %d)",
                        username, hiscores.getCombatLevel(), hiscores.getAttack().getLevel(), hiscores.getStrength()
                        .getLevel(), hiscores.getDefence().getLevel(), hiscores.getHitpoints().getLevel(),
                        hiscores.getPrayer().getLevel(), hiscores.getRanged().getLevel(), hiscores.getMagic()
                        .getLevel()), this);

            } catch (IOException e) {
                bot.sendMessage(channel, executor, "Unable to retrieve combat information for " + username, this);
            }
        }
    }
}
