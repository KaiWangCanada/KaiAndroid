package kai.kaiprivate.thirdparty.guava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import java.util.List;

import kai.kaiprivate.R;

public class KaiGuavaOrderList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kai_guava_order_list);

//        order_list_of_integer_least_to_greatest();
        order_by_object_field();

    }

    public void order_list_of_integer_least_to_greatest () {

        List<Integer> startingLineUp = Lists.newArrayList(73, 58, 66, 57, 32, 88, 90, 12, 15, 99,
                11);

        List<Integer> startingLineUpInOrder = Ordering
                .natural()
                .leastOf(startingLineUp, startingLineUp.size());

        for(int i = 0; i < startingLineUpInOrder.size(); i++) {
            System.out.println(startingLineUpInOrder.get(i));
        }

//        assertEquals(new Integer(11), startingLineUpInOrder.get(0));
    }

    class GlassWare {

        private String name;
        private String description;

        public GlassWare(String name, String description) {
            super();
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

    }

    public void order_by_object_field () {

        List<GlassWare> beerGlasses = Lists.newArrayList(new GlassWare("Flute Glass", "Enhances " +
                "and showcases..."), new GlassWare("Pilsner Glass (or Pokal)", "showcases color, " +
                "..."), new GlassWare("Pint Glass", "cheap to make..."), new GlassWare("Goblet " +
                "(or Chalice)", "Eye candy..."), new GlassWare("Mug (or Seidel, Stein)", "Easy to" +
                " drink..."), new GlassWare(null, null));

        Ordering<GlassWare> byGlassWareName = Ordering.natural().nullsFirst()
                .onResultOf(new Function<GlassWare, String>() {
                    public String apply(GlassWare glassWare) {
                        return glassWare.getName();
                    }
                });

        List<GlassWare> sortedGlassWare = byGlassWareName.immutableSortedCopy(beerGlasses);

        for(int i = 0; i < sortedGlassWare.size(); i++) {
            System.out.println(sortedGlassWare.get(i).getName());
        }

//        GlassWare firstBeerGlass = byGlassWareName.min(beerGlasses);

        // first element will be null
//        assertNull(firstBeerGlass.getName());

//        GlassWare lastBeerGlass = byGlassWareName.max(beerGlasses);
//        assertEquals("Pint Glass", lastBeerGlass.getName());
    }
}
