package kelly;

import org.junit.Assert;
import org.junit.Test;

public class GridTests {
    @Test
    public void testPack0() {
        Assert.assertArrayEquals("3 spaces in front"
                , new int[]{2,0,0,0}
                , Grid.pack0(0, new int[]{0,0,0,2})
        );

        Assert.assertArrayEquals("2 spaces in front"
                , new int[]{2,2,0,0}
                , Grid.pack0(0, new int[]{0,0,2,2})
        );

        Assert.assertArrayEquals("1 spaces in front"
                , new int[]{2,2,2,0}
                , Grid.pack0(0, new int[]{0,2,2,2})
        );

        Assert.assertArrayEquals("0 spaces in front"
                , new int[]{2,2,2,2}
                , Grid.pack0(0, new int[]{2,2,2,2})
        );

        Assert.assertArrayEquals("1 space in front 1 space in between"
                , new int[]{2,2,0,0}
                , Grid.pack0(0, new int[]{0,2,0,2})
        );

        Assert.assertArrayEquals("0 space in front 1 space in between"
                , new int[]{2,2,0,0}
                , Grid.pack0(0, new int[]{2,0,2,0})
        );

        Assert.assertArrayEquals("2 spaces in between"
                , new int[]{2,2,0,0}
                , Grid.pack0(0, new int[]{2,0,0,2})
        );
    }

    @Test
    public void testPackX() {
        Assert.assertArrayEquals("3 spaces in front"
                , new int[]{2,0,0,0}
                , Grid.packX(0, new int[]{0,0,0,2})
        );

        Assert.assertArrayEquals("2 spaces in front"
                , new int[]{4,0,0,0}
                , Grid.packX(0, new int[]{0,0,2,2})
        );

        Assert.assertArrayEquals("2 spaces in between"
                , new int[]{4,0,0,0}
                , Grid.packX(0, new int[]{0,2,0,2})
        );

        Assert.assertArrayEquals("3 spaces in between"
                , new int[]{4,0,0,0}
                , Grid.packX(0, new int[]{2,0,0,2})
        );

        Assert.assertArrayEquals("0,2,2,2"
                , new int[]{4,2,0,0}
                , Grid.packX(0, new int[]{0,2,2,2})
        );

        Assert.assertArrayEquals("0,2,4,2"
                , new int[]{2,4,2,0}
                , Grid.packX(0, new int[]{0,2,4,2})
        );

        Assert.assertArrayEquals("0,2,4,4"
                , new int[]{2,8,0,0}
                , Grid.packX(0, new int[]{0,2,4,4})
        );

        Assert.assertArrayEquals("0,2,2,4"
                , new int[]{4,4,0,0}
                , Grid.packX(0, new int[]{0,2,2,4})
        );
    }
}
