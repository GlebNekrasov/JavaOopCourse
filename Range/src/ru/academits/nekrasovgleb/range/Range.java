package ru.academits.nekrasovgleb.range;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double number) {
        return number >= from && number <= to;
    }

    public Range getIntersection(Range range2) {
        double from2 = range2.getFrom();
        double to2 = range2.getTo();

        if (to <= from2 || to2 <= from) {
            return null;
        }

        double intersectionFrom = Math.max(from, from2);
        double intersectionTo = Math.min(to, to2);

        return new Range(intersectionFrom, intersectionTo);
    }

    public Range[] getUnion(Range range2) {
        double from2 = range2.getFrom();
        double to2 = range2.getTo();

        if (to >= from2 && from <= to2 ) {
            double unionFrom = Math.min(from, from2);
            double unionTo = Math.max(to, to2);

            Range unionRange = new Range(unionFrom, unionTo);

            return new Range[] {unionRange};
        }

        return new Range[] {this, range2};
    }

    public Range[] getDifference(Range range2) {
        double from2 = range2.getFrom();
        double to2 = range2.getTo();

        if (from >= from2 && to <= to2) {
            return null;
        }

        if (this.getIntersection(range2) == null) {
            return new Range[] {this};
        }

        if (from < from2 && to > to2) {
            Range differenceRange1 = new Range(from, from2);
            Range differenceRange2 = new Range(to2, to);
            return new Range[] {differenceRange1, differenceRange2};
        }

        Range differenceRange;

        if (from < from2) {
            differenceRange = new Range(from, from2);
        } else {
            differenceRange = new Range(to2, to);
        }

        return new Range[] {differenceRange};
    }
}
