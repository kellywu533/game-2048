package kelly;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class GridTests {
    Grid g = new Grid(4, 4);

    private void setupGrid(int[] arr) {
        for(int i=0; i<arr.length; i++) {
            g.putValue(i, arr[i]);
        }
    }

    @Test
    public void testPack() {
        setupGrid(
            new int[] {
                    0, 2, 4, 8
                    , 0, 0, 0, 0
                    , 8, 16, 2, 8
                    , 0, 0, 0, 0
            }
        );

        Assert.assertFalse(g.pack(Grid.Move.RIGHT));
        Assert.assertArrayEquals(new int[] {
                0, 2, 4, 8
                , 0, 0, 0, 0
                , 8, 16, 2, 8
                , 0, 0, 0, 0
        }, g.getValues());
    }

    @Ignore
    //TODO fix pack0 method
    @Test
    public void testPack0() {
        setupGrid(
                new int[] {
                        0, 2, 0, 8
                        , 2, 0, 0, 0
                        , 2, 0, 2, 0
                        , 0, 2, 0, 0
                }
        );

        Assert.assertTrue(g.pack0(Grid.Move.RIGHT));
        Assert.assertArrayEquals(new int[] {
                0, 0, 2, 8
                , 0, 2, 0, 0
                , 0, 2, 0, 2
                , 0, 0, 2, 0
        }, g.getValues());
    }
}
