package filteredOnes.udemy.a04_concurreny_challenges_solutions;

class a5_MinMaxMetricTask {

    static class MinMaxMetrics {
        // Add all necessary member variables
        private volatile long min;
        private volatile long max;
        /**
         * Initializes all member variables
         */
        public MinMaxMetrics() {
            // Add code here
            this.min = Long.MAX_VALUE;
            this.max = Long.MIN_VALUE;
        }

        /**
         * Adds a new sample to our metrics.
         */
        public synchronized void addSample(long newSample) {
            // Add code here
            min = Math.min(min, newSample);
            max = Math.max(max, newSample);
        }

        /**
         * Returns the smallest sample we've seen so far.
         */
        public long getMin() {
            // Add code here
            return min;
        }

        /**
         * Returns the biggest sample we've seen so far.
         */
        public long getMax() {
            // Add code here
            return max;
        }
    }
}
