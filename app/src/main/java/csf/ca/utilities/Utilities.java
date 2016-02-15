package csf.ca.utilities;

/**
 * Created by Alexis on 2016-01-26.
 */
public class Utilities
{
    private static final int MILLISECOND_IN_HOURS_MULTIPLIER = 3600000;
    private static final int HOURS_IN_MINUTES_MULTIPLIER = 60000;
    private static final int MINUTES_IN_SECONDS_MULTIPLIER = 1000;

    private static final int PERCENTAGE_MULTIPLIER = 100;

    private static final int NULL_TIMER = 0;
    private static final int TWO_CHAR_NUMBER_LIMIT = 10;

    private static final double NULL_DOUBLE = (double) 0;

    private static final String TIMER_NUMBER_SEPARATOR = ":";
    private static final String EMPTY_STRING = "";

    private static final String CHAR_NUMBER_ZERO = "0";

    public String timerFormat(long milliseconds)
    {
        String timerHourMinute = EMPTY_STRING;
        String timerSecond = EMPTY_STRING;

        int nbHours = (int)(milliseconds / MILLISECOND_IN_HOURS_MULTIPLIER);
        int nbMinutes = (int) ((milliseconds % MILLISECOND_IN_HOURS_MULTIPLIER) / HOURS_IN_MINUTES_MULTIPLIER);
        int nbSeconds = (int) (((milliseconds % MILLISECOND_IN_HOURS_MULTIPLIER) % HOURS_IN_MINUTES_MULTIPLIER) / MINUTES_IN_SECONDS_MULTIPLIER);

        if(nbHours > NULL_TIMER)
        {
            timerHourMinute = nbHours + TIMER_NUMBER_SEPARATOR;
        }

        if(nbSeconds < TWO_CHAR_NUMBER_LIMIT)
        {
            timerSecond = CHAR_NUMBER_ZERO + nbSeconds;
        }
        else
        {
            timerSecond = EMPTY_STRING + nbSeconds;
        }

        return timerHourMinute + nbMinutes + TIMER_NUMBER_SEPARATOR + timerSecond;
    }

    public int getCurrentProgressPercentage(long currentProgress, long totalDuration)
    {
        double percentage = NULL_DOUBLE;

        long currentSeconds = (int) (currentProgress / MINUTES_IN_SECONDS_MULTIPLIER);
        long totalSeconds = (int) (totalDuration / MINUTES_IN_SECONDS_MULTIPLIER);

        percentage = (((double)currentSeconds)/totalSeconds) * PERCENTAGE_MULTIPLIER;

        return (int) percentage;
    }

    public int updateProgress(int progress, int songLength)
    {
        int currentProgress = NULL_TIMER;
        int songLengthSecondFormat = (songLength / 1000);

        currentProgress = (int) ((((double) progress) / PERCENTAGE_MULTIPLIER)  * songLengthSecondFormat);
        currentProgress = currentProgress * MINUTES_IN_SECONDS_MULTIPLIER;

        return currentProgress;
    }
}
