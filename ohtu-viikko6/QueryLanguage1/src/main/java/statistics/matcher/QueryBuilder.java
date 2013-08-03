package statistics.matcher;

public class QueryBuilder {

    Matcher matcher;

    public QueryBuilder() {
    }

    public QueryBuilder(Matcher matcher) {
        this.matcher = matcher;
    }

    public QueryBuilder playsIn(String team) {
        if (matcher == null) {
            return new QueryBuilder(new PlaysIn(team));
        }
        return new QueryBuilder(new And(matcher, new PlaysIn(team)));
    }

    public QueryBuilder hasAtLeast(int amountOf, String type) {
        if (matcher == null) {
            return new QueryBuilder(new HasAtLeast(amountOf, type));
        }
        return new QueryBuilder(new And(matcher, new HasAtLeast(amountOf, type)));
    }

    public QueryBuilder hasFewerThan(int amountOf, String type) {
        if (matcher == null) {
            return new QueryBuilder(new HasFewerThan(amountOf, type));
        }
        return new QueryBuilder(new And(matcher, new HasFewerThan(amountOf, type)));
    }

    public Matcher build() {
        return matcher;
    }

    public QueryBuilder oneOf(Matcher...matchers) {
        return new QueryBuilder(new Or(matchers));
    }
}
