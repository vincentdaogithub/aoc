package com.vincentdao.aoc._2023;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class _7 {

    private static final Pattern CARDS_BID_PATTERN = Pattern.compile("^(\\w+) (\\d+)$");

    private static final List<Character> CARD_RANKS;
    static {
        CARD_RANKS = new ArrayList<>(13);
        CARD_RANKS.add('A');
        CARD_RANKS.add('K');
        CARD_RANKS.add('Q');
        CARD_RANKS.add('J');
        CARD_RANKS.add('T');
        CARD_RANKS.add('9');
        CARD_RANKS.add('8');
        CARD_RANKS.add('7');
        CARD_RANKS.add('6');
        CARD_RANKS.add('5');
        CARD_RANKS.add('4');
        CARD_RANKS.add('3');
        CARD_RANKS.add('2');
    }

    private static final List<Character> JOKER_CARD_RANKS;
    static {
        JOKER_CARD_RANKS = new ArrayList<>(13);
        JOKER_CARD_RANKS.add('A');
        JOKER_CARD_RANKS.add('K');
        JOKER_CARD_RANKS.add('Q');
        JOKER_CARD_RANKS.add('T');
        JOKER_CARD_RANKS.add('9');
        JOKER_CARD_RANKS.add('8');
        JOKER_CARD_RANKS.add('7');
        JOKER_CARD_RANKS.add('6');
        JOKER_CARD_RANKS.add('5');
        JOKER_CARD_RANKS.add('4');
        JOKER_CARD_RANKS.add('3');
        JOKER_CARD_RANKS.add('2');
        JOKER_CARD_RANKS.add('J');
    }

    @SuppressWarnings("Duplicates")
    private static class DefaultComparator implements Comparator<CardsBid> {

        @Override
        public int compare(CardsBid o1, CardsBid o2) {
            int count1 = getUniqueCharsCount(o1.cards);
            int count2 = getUniqueCharsCount(o2.cards);
            if (count1 != count2) {
                return (count2 - count1);
            }
            // Five of a kind
            if (count1 == 1) {
                return (getRank(o2.cards[0]) - getRank(o1.cards[0]));
            }
            // Sort for ease of checking
            char[] cards1 = Arrays.copyOf(o1.cards, o1.cards.length);
            char[] cards2 = Arrays.copyOf(o2.cards, o2.cards.length);
            Arrays.sort(cards1);
            Arrays.sort(cards2);
            // Four of a kind or Full house
            if (count1 == 2) {
                boolean cards1IsFourOfAKind = isFourOfAKind(cards1);
                boolean cards2IsFourOfAKind = isFourOfAKind(cards2);
                if (cards1IsFourOfAKind != cards2IsFourOfAKind) {
                    return cards1IsFourOfAKind ? 1 : -1;
                }
                // Same rank
                return compareCardsWithSameType(o1.cards, o2.cards);
            }
            // Three of a kind or two pair
            if (count1 == 3) {
                boolean cards1IsThreeOfAKind = isThreeOfAKind(cards1);
                boolean cards2IsThreeOfAKind = isThreeOfAKind(cards2);
                if (cards1IsThreeOfAKind != cards2IsThreeOfAKind) {
                    return cards1IsThreeOfAKind ? 1 : -1;
                }
                // Same rank
                return compareCardsWithSameType(o1.cards, o2.cards);
            }
            return compareCardsWithSameType(o1.cards, o2.cards);
        }

        protected int getRank(char c) {
            return CARD_RANKS.indexOf(c);
        }

        protected int getUniqueCharsCount(char[] chars) {
            Set<Character> result = new HashSet<>();
            for (char c : chars) {
                result.add(c);
            }
            return result.size();
        }

        protected boolean isFourOfAKind(char[] chars) {
            return ((chars[0] == chars[3]) || (chars[1] == chars[4]));
        }

        protected boolean isThreeOfAKind(char[] chars) {
            return ((chars[0] == chars[2]) || (chars[1] == chars[3]) || (chars[2] == chars[4]));
        }

        protected int compareCardsWithSameType(char[] cards1, char[] cards2) {
            for (int i = 0; i < cards1.length; i++) {
                char c1 = cards1[i];
                char c2 = cards2[i];
                if (c1 != c2) {
                    return getRank(c2) - getRank(c1);
                }
            }
            return 0;
        }
    }

    @SuppressWarnings("Duplicates")
    private static class JokerComparator extends DefaultComparator {

        @Override
        public int compare(CardsBid o1, CardsBid o2) {
            int jokersCount1 = getJokersCount(o1.cards);
            int jokersCount2 = getJokersCount(o2.cards);
            if ((jokersCount1 == 0) && (jokersCount2 == 0)) {
                return super.compare(o1, o2);
            }
            int count1 = getUniqueCharsCount(o1.cards);
            int count2 = getUniqueCharsCount(o2.cards);
            if (jokersCount1 > 0) {
                count1 = getCountAfterJoker(count1);
            }
            if (jokersCount2 > 0) {
                count2 = getCountAfterJoker(count2);
            }
            if (count1 != count2) {
                return (count2 - count1);
            }
            // Sort for ease of checking
            char[] cards1 = Arrays.copyOf(o1.cards, o1.cards.length);
            char[] cards2 = Arrays.copyOf(o2.cards, o2.cards.length);
            Arrays.sort(cards1);
            Arrays.sort(cards2);
            // Four of a kind or Full house
            if (count1 == 2) {
                boolean cards1IsFourOfAKind = ((isThreeOfAKind(cards1) && (jokersCount1 > 0))
                        || (jokersCount1 == 2)
                        || isFourOfAKind(cards1));
                boolean cards2IsFourOfAKind = ((isThreeOfAKind(cards2) && (jokersCount2 > 0))
                        || (jokersCount2 == 2)
                        || isFourOfAKind(cards2));
                if (cards1IsFourOfAKind != cards2IsFourOfAKind) {
                    return cards1IsFourOfAKind ? 1 : -1;
                }
                // Same rank
                return compareCardsWithSameType(o1.cards, o2.cards);
            }
            // Three of a kind or two pair
            if (count1 == 3) {
                boolean cards1IsThreeOfAKind = ((jokersCount1 > 0) || isThreeOfAKind(cards1));
                boolean cards2IsThreeOfAKind = ((jokersCount2 > 0) || isThreeOfAKind(cards2));
                if (cards1IsThreeOfAKind != cards2IsThreeOfAKind) {
                    return cards1IsThreeOfAKind ? 1 : -1;
                }
                // Same rank
                return compareCardsWithSameType(o1.cards, o2.cards);
            }
            return compareCardsWithSameType(o1.cards, o2.cards);
        }

        @Override
        protected int getRank(char c) {
            return JOKER_CARD_RANKS.indexOf(c);
        }

        private int getCountAfterJoker(int count) {
            if (count == 1) {
                return 1;
            }
            return (count - 1);
        }

        private int getJokersCount(char[] chars) {
            int count = 0;
            for (char c : chars) {
                if (c == 'J') {
                    count++;
                }
            }
            return count;
        }
    }

    private record CardsBid(char[] cards, int bid) {

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (Objects.isNull(object) || (getClass() != object.getClass())) {
                return false;
            }
            CardsBid cardsBid = (CardsBid) object;
            return (bid == cardsBid.bid)
                    && Arrays.equals(cards, cardsBid.cards);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(bid);
            result = 31 * result + Arrays.hashCode(cards);
            return result;
        }

        @Override
        public String toString() {
            return "CardsBid{" +
                    "cards=" + Arrays.toString(cards) +
                    ", bid=" + bid +
                    '}';
        }
    }

    public static void main(String[] args) {
        try (InputStream is = _7.class.getClassLoader().getResourceAsStream("2023/7.txt")) {
            if (Objects.isNull(is)) {
                System.err.println("Cannot load input file.");
                return;
            }
            List<CardsBid> cardsBids = new LinkedList<>();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while (Objects.nonNull(line = br.readLine())) {
                    cardsBids.add(readCardsAndBid(line));
                }
            }
            long totalWinnings = getTotalWinnings(new ArrayList<>(cardsBids));
            long totalWinningsWithJokerCard = getTotalWinningsWithJokerCard(new ArrayList<>(cardsBids));
            System.out.println("Total winnings: " + totalWinnings);
            System.out.println("Total winnings with Joker card: " + totalWinningsWithJokerCard);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static CardsBid readCardsAndBid(String line) {
        Matcher matcher = CARDS_BID_PATTERN.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Unknown cards bid");
        }
        return new CardsBid(matcher.group(1).toCharArray(), Integer.parseInt(matcher.group(2)));
    }

    private static long getTotalWinnings(List<CardsBid> cardsBids) {
        cardsBids.sort(new DefaultComparator());
        long result = 0;
        for (int i = 0; i < cardsBids.size(); i++) {
            result += (long) cardsBids.get(i).bid * (i + 1);
        }
        return result;
    }

    private static long getTotalWinningsWithJokerCard(List<CardsBid> cardsBids) {
        cardsBids.sort(new JokerComparator());
        long result = 0;
        for (int i = 0; i < cardsBids.size(); i++) {
            result += (long) cardsBids.get(i).bid * (i + 1);
        }
        return result;
    }
}
