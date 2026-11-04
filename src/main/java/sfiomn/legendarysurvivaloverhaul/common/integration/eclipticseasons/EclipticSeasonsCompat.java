package sfiomn.legendarysurvivaloverhaul.common.integration.eclipticseasons;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;

import java.lang.reflect.Method;

/**
 * Reflection-based access to the Ecliptic Seasons API, using the method names of the
 * 26.3 release (0.15.x). All methods return sensible defaults if the API is not present.
 */
public final class EclipticSeasonsCompat
{
    private static final String API_CLASS = "com.teamtea.eclipticseasons.api.EclipticSeasonsApi";

    private EclipticSeasonsCompat()
    {
    }

    private static Object apiInstance() throws Exception
    {
        Class<?> api = Class.forName(API_CLASS);
        Method getInstance = api.getMethod("getInstance");
        return getInstance.invoke(null);
    }

    private static Object invokeApi(String method, Class<?>[] parameterTypes, Object... args) throws Exception
    {
        Class<?> api = Class.forName(API_CLASS);
        Method m = api.getMethod(method, parameterTypes);
        return m.invoke(apiInstance(), args);
    }

    public static boolean isSeasonEnabled(Level level)
    {
        try
        {
            Object res = invokeApi("isSeasonEnabled", new Class<?>[]{Level.class}, level);
            return res instanceof Boolean b && b;
        } catch (Throwable t)
        {
            return false;
        }
    }

    public static Object getSolarTerm(Level level)
    {
        try
        {
            return invokeApi("getSolarTerm", new Class<?>[]{Level.class}, level);
        } catch (Throwable t)
        {
            return null;
        }
    }

    public static boolean isSolarTermNone(Level level)
    {
        Object term = getSolarTerm(level);
        if (term == null) return true;
        try
        {
            Method name = term.getClass().getMethod("name");
            return "NONE".equals(String.valueOf(name.invoke(term)));
        } catch (Throwable t)
        {
            return true;
        }
    }

    public static int getSolarTermDayTime(Level level)
    {
        Object term = getSolarTerm(level);
        if (term == null) return 12000;
        try
        {
            Method m = term.getClass().getMethod("getDayTime");
            Object res = m.invoke(term);
            return res instanceof Number n ? n.intValue() : 12000;
        } catch (Throwable t)
        {
            return 12000;
        }
    }

    public static int getLastingDaysOfEachTerm(Level level)
    {
        try
        {
            Object res = invokeApi("getLastingDaysOfEachTerm", new Class<?>[]{Level.class}, level);
            return res instanceof Number n ? n.intValue() : 0;
        } catch (Throwable t)
        {
            return 0;
        }
    }

    public static int getSolarDays(Level level)
    {
        try
        {
            Object res = invokeApi("getSolarDays", new Class<?>[]{Level.class}, level);
            return res instanceof Number n ? n.intValue() : 0;
        } catch (Throwable t)
        {
            return 0;
        }
    }

    /**
     * Day index inside the current solar term (0-based).
     */
    public static int getTimeInTerm(Level level)
    {
        try
        {
            Object res = invokeApi("getTimeInTerm", new Class<?>[]{Level.class}, level);
            return res instanceof Number n ? n.intValue() : 0;
        } catch (Throwable t)
        {
            return 0;
        }
    }

    public static Biome.Precipitation getCurrentPrecipitationAt(Level level, BlockPos pos)
    {
        try
        {
            Object res = invokeApi("getCurrentPrecipitationAt", new Class<?>[]{Level.class, BlockPos.class}, level, pos);
            return res instanceof Biome.Precipitation precipitation ? precipitation : Biome.Precipitation.NONE;
        } catch (Throwable t)
        {
            return Biome.Precipitation.NONE;
        }
    }

    public static int getSolarTermOrdinal(Level level)
    {
        Object term = getSolarTerm(level);
        if (term == null) return 0;
        try
        {
            Method m = term.getClass().getMethod("ordinal");
            Object res = m.invoke(term);
            return res instanceof Number n ? n.intValue() : 0;
        } catch (Throwable t)
        {
            return 0;
        }
    }

    public static Component getSolarTermTranslation(Level level)
    {
        Object term = getSolarTerm(level);
        if (term == null) return Component.empty();
        try
        {
            Method m = term.getClass().getMethod("getTranslation");
            Object res = m.invoke(term);
            return res instanceof Component component ? component : Component.empty();
        } catch (Throwable t)
        {
            return Component.empty();
        }
    }

    public static Component getSeasonTranslation(Level level)
    {
        Object term = getSolarTerm(level);
        if (term == null) return Component.empty();
        try
        {
            Method getSeason = term.getClass().getMethod("getSeason");
            Object season = getSeason.invoke(term);
            if (season == null) return Component.empty();
            Method getTranslation = season.getClass().getMethod("getTranslation");
            Object res = getTranslation.invoke(season);
            return res instanceof Component component ? component : Component.empty();
        } catch (Throwable t)
        {
            return Component.empty();
        }
    }
}