package com.hammerdev.challengesaoc;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main(String[] args)
    {
        BasicConfigurator.configure();

        App app = new App(LogManager.getLogger(App.class));
        app.firstPart(true);
        app.secondPart(true);
    }

    public final Logger logger;

    public App(Logger logger)
    {
        this.logger = logger;
        this.logger.setLevel(Level.ALL);
    }

    public void secondPart(boolean test)
    {
        logger.info("------------- [STARTING SECOND PART ] ------------");
        String file = (test ? "p2_test_data.txt" : "p2_data.txt");
        Scanner fileScanner = new Scanner(new InputStreamReader(App.class.getResourceAsStream(file)));

        List<Game> games = new LinkedList<>();

        int totalSum = 0;

        while (fileScanner.hasNextLine()) {
            String fileBufferLine = fileScanner.nextLine();

            Matcher gameIdMatcher = regex("(Game[ ])([0-9]+)", fileBufferLine);
            if (gameIdMatcher.find()) {
                Integer gameId = Integer.valueOf(gameIdMatcher.group(2));
                Game game = new Game(gameId);
                

                // BLUES
                Matcher blueMatcher = regex("([0-9]+)[ ](%s)".formatted(Game.GameCubes.CUBES_MATCH_BLUE), fileBufferLine);
                if (blueMatcher.find()) {
                    game.setMinBlueCubes(Integer.valueOf(blueMatcher.group(1)));
                    Iterator<MatchResult> iteratorMatchResult = blueMatcher.results().iterator();
                    while (iteratorMatchResult.hasNext()) {
                        MatchResult matchResult = iteratorMatchResult.next();
                        if (Integer.valueOf(matchResult.group(1)) > game.getMinBlueCubes()) game.setMinBlueCubes(Integer.valueOf(matchResult.group(1))); 
                    }
                }

                // GREEN
                Matcher greenMatcher = regex("([0-9]+)[ ](%s)".formatted(Game.GameCubes.CUBES_MATCH_GREEN), fileBufferLine);
                if (greenMatcher.find()) {
                    game.setMinGreenCubes(Integer.valueOf(greenMatcher.group(1)));
                    Iterator<MatchResult> iteratorMatchResult = greenMatcher.results().iterator();
                    while (iteratorMatchResult.hasNext()) {
                        MatchResult matchResult = iteratorMatchResult.next();
                        if (Integer.valueOf(matchResult.group(1)) > game.getMinGreenCubes()) game.setMinGreenCubes(Integer.valueOf(matchResult.group(1))); 
                    }
                }

                // RED
                Matcher redMatcher = regex("([0-9]+)[ ](%s)".formatted(Game.GameCubes.CUBES_MATCH_RED), fileBufferLine);
                if (redMatcher.find()) {
                    game.setMinRedCubes(Integer.valueOf(redMatcher.group(1)));
                    Iterator<MatchResult> iteratorMatchResult = redMatcher.results().iterator();
                    while (iteratorMatchResult.hasNext()) {
                        MatchResult matchResult = iteratorMatchResult.next();
                        if (Integer.valueOf(matchResult.group(1)) > game.getMinRedCubes()) game.setMinRedCubes(Integer.valueOf(matchResult.group(1))); 
                    }
                }

                logger.info("GAME: %d - NEEDS MIN: %d BLUES; %d GREENS; %d REDS".formatted(gameId, game.getMinBlueCubes(), game.getMinGreenCubes(), game.getMinRedCubes()));

                games.add(game);

                totalSum += (game.getMinBlueCubes() * game.getMinGreenCubes() * game.getMinRedCubes());
            }
        }
        logger.info("TOTAL SUM: %d".formatted(totalSum));
    }

    public void firstPart(boolean test)
    {
        logger.info("------------- [STARTING FIRST PART ] ------------");
        int totalGameIdsSum = 0;
        List<Game> listOfGames = new LinkedList<>();

        String file = (test ? "p1_test_data.txt" : "p1_data.txt");
        Scanner fileScanner = new Scanner(new InputStreamReader(App.class.getResourceAsStream(file)));

        while (fileScanner.hasNextLine()) {
            String fileBufferLine = fileScanner.nextLine();

            Matcher gameIdMatcher = regex("(Game[ ])([0-9]+)", fileBufferLine);

            if (gameIdMatcher.find()) {
                Integer gameId = Integer.valueOf(gameIdMatcher.group(2));
                Game game = new Game(gameId);

                // BLUE MATCHER
                Matcher blueMatcher = regex("([0-9]+)[ ](%s)".formatted(Game.GameCubes.CUBES_MATCH_BLUE), fileBufferLine);
                if (blueMatcher.find()) {
                    if (Integer.valueOf(blueMatcher.group(1)) > Game.GameCubes.MAX_CUBES_BLUE) {
                        game.setValid(false);
                    } else {
                        Iterator<MatchResult> iteratorBMatchResult = blueMatcher.results().iterator();
                        while (iteratorBMatchResult.hasNext()) {
                            if (Integer.valueOf(iteratorBMatchResult.next().group(1)) > Game.GameCubes.MAX_CUBES_BLUE) {
                                game.setValid(false);
                                break;
                            }
                        }
                    }
                }

                // GREEN MATCHER
                Matcher greenMatcher = regex("([0-9]+)[ ](%s)".formatted(Game.GameCubes.CUBES_MATCH_GREEN), fileBufferLine);
                if (greenMatcher.find()) {

                    if (Integer.valueOf(greenMatcher.group(1)) > Game.GameCubes.MAX_CUBES_GREEN) {
                        game.setValid(false);
                    } else {
                        Iterator<MatchResult> iteratorGMatchResult = greenMatcher.results().iterator();
                        while (iteratorGMatchResult.hasNext()) {
                            if (Integer.valueOf(iteratorGMatchResult.next().group(1)) > Game.GameCubes.MAX_CUBES_GREEN) {
                                game.setValid(false);
                                break;
                            }
                        }
                    }
                }

                // RED MATCHER
                Matcher redMatcher = regex("([0-9]+)[ ](%s)".formatted(Game.GameCubes.CUBES_MATCH_RED), fileBufferLine);
                if (redMatcher.find()) {
                    if (Integer.valueOf(redMatcher.group(1)) > Game.GameCubes.MAX_CUBES_RED) {
                        game.setValid(false);
                    } else {
                        Iterator<MatchResult> iteratorRMatchResult = redMatcher.results().iterator();
                        while (iteratorRMatchResult.hasNext()) {
                            if (Integer.valueOf(iteratorRMatchResult.next().group(1)) > Game.GameCubes.MAX_CUBES_RED) {
                                game.setValid(false);
                                break;
                            }
                        }
                    }
                }

                logger.info("GAME: %d IS VALID? %b".formatted(gameId, game.isValid()));
                listOfGames.add(game);
                if (game.isValid()) {
                    totalGameIdsSum += game.getId();
                }
            }
        }

        logger.info("TOTAL SUM: " + totalGameIdsSum);
    }

    public Matcher regex(String regex, String input)
    {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        return matcher;
    }

    protected static class Game
    {
        static interface GameCubes {
            public static final String CUBES_MATCH_RED = "red";
            public static final String CUBES_MATCH_GREEN = "green";
            public static final String CUBES_MATCH_BLUE = "blue";

            public static final int MAX_CUBES_RED = 12;
            public static final int MAX_CUBES_GREEN = 13;
            public static final int MAX_CUBES_BLUE = 14;
        }

        protected final int id;
        protected boolean isValid = true;

        protected int minRedCubes = 0;
        protected int minGreenCubes = 0;
        protected int minBlueCubes = 0;

        protected Game(int id)
        {
            this.id = id;
        }

        public int getId()
        {
            return id;
        }

        public boolean isValid()
        {
            return isValid;
        }

        public void setValid(boolean isValid)
        {
            this.isValid = isValid;
        }

        public int getMinRedCubes()
        {
            return minRedCubes;
        }

        public void setMinRedCubes(int minRedCubes)
        {
            this.minRedCubes = minRedCubes;
        }

        public int getMinGreenCubes()
        {
            return minGreenCubes;
        }

        public void setMinGreenCubes(int minGreenCubes)
        {
            this.minGreenCubes = minGreenCubes;
        }

        public int getMinBlueCubes()
        {
            return minBlueCubes;
        }

        public void setMinBlueCubes(int minBlueCubes)
        {
            this.minBlueCubes = minBlueCubes;
        }
    }
}
