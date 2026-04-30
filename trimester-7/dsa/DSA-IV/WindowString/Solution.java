public class Solution {
    public String minWindow(String A, String B) {
        if (A == null || B == null || A.length() == 0 || B.length() == 0
                || A.length() < B.length()) {
            return "";
        }

        int[] freq = new int[128];
        for (char c : B.toCharArray()) {
            freq[c]++;
        }

        // 'uniqueCharsRequired' tracks how many DISTINCT characters we need to fulfill.
        int uniqueCharsRequired = 0;
        for (int count : freq) {
            if (count > 0) {
                uniqueCharsRequired++;
            }
        }

        int left = 0;
        int right = 0;
        int uniqueCharsFormed = 0;

        // Tracks the frequencies of characters currently inside our sliding window.
        int[] windowFreq = new int[128];

        // Variables to keep track of the minimum window we've discovered so far.
        int minWindowLength = Integer.MAX_VALUE;
        int minWindowStart = 0;

        // We expand our window by moving the 'right' pointer across string A.
        while (right < A.length()) {

            // Add the current character to our window's frequency map.
            char rightChar = A.charAt(right);
            windowFreq[rightChar]++;

            // If this character is one we need, AND we have exactly the right amount in our window,
            // we've successfully fulfilled the requirement for this specific character.
            if (freq[rightChar] > 0 && windowFreq[rightChar] == freq[rightChar]) {
                uniqueCharsFormed++;
            }

            // Once we have fulfilled ALL required distinct characters, our window is valid.
            // Now, we try to optimize it by shrinking the window from the left.
            while (left <= right && uniqueCharsFormed == uniqueCharsRequired) {

                // Record the current window if it is strictly smaller than our best so far.
                int currentWindowLength = right - left + 1;
                if (currentWindowLength < minWindowLength) {
                    minWindowLength = currentWindowLength;
                    minWindowStart = left;
                }

                // Prepare to move the 'left' pointer forward by removing its character from our
                // window map.
                char leftChar = A.charAt(left);
                windowFreq[leftChar]--;

                // If removing this character breaks our required frequency, we note that the window
                // is no longer valid.
                if (freq[leftChar] > 0 && windowFreq[leftChar] < freq[leftChar]) {
                    uniqueCharsFormed--;
                }

                // Shrink the window.
                left++;
            }

            // Expand the window.
            right++;
        }

        // If 'minWindowLength' was never updated, no valid window was found, so we return an empty
        // string.
        // Otherwise, we return the minimum substring using the start index and length.
        return minWindowLength == Integer.MAX_VALUE ? ""
                : A.substring(minWindowStart, minWindowStart + minWindowLength);
    }
}
