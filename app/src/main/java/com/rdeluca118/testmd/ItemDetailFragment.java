package com.rdeluca118.testmd;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rdeluca118.testmd.dummy.DummyContent;

import java.util.ArrayList;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.gameDate);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_item_detail, container, false);

        Player Player1, Player2;
        Player winner, looser;
        Game game;
        Cursor c;

        if (mItem != null) {
            DBManager dbm = new DBManager(getContext());
            dbm.open();

            c = dbm.getOneGame(Integer.valueOf(mItem.id));
            game = getTheGame(dbm, Integer.valueOf(mItem.id));
            int p1ID = c.getInt(2);
            int p2ID = c.getInt(3);
            c.close();

            Player1 = getOnePlayer(dbm, p1ID);
            game.setPlayer1(Player1);
            Player2 = getOnePlayer(dbm, p2ID);
            game.setPlayer2(Player2);

//            winner = null;
//            looser = null;
            int winnerID = game.getWinnerId();
            if (winnerID == p1ID) {
                winner = Player1;
                looser = Player2;
            } else {
                winner = Player2;
                looser = Player1;
            }
            Leg[] legs = getLegs(dbm, game.getId());
            for (Leg i : legs) {
                Turn[] t = getTurns(dbm, i.getLegId());
            }

            dbm.close();
            // get legs of game _id mItem.id by leg id
            // get turns of leg by leg id

            Spanned pane1 = Html.fromHtml("Win : <h2>" + winner.getName() + "</h2><br><p>Record:</p><p><h4>" + winner.getWins() + " W --- " + winner.getLosses() + " L</h4></p>");
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(pane1);
            Spanned pane2 = Html.fromHtml("Loss : <h2>" + looser.getName() + "</h2><br><p>Record:</p><p><h4>" + looser.getWins() + " W --- " + looser.getLosses() + " L</h4></p>");
            ((TextView) rootView.findViewById(R.id.looser_text)).setText(pane2);
        }
        // Show the dummy content as text in a TextView.

        return rootView;
    }

    private Game getTheGame(DBManager dbm, int gameID) {
        Player emptyPlayer = new Player();
        Game game = new Game(emptyPlayer, emptyPlayer, 0);
        Cursor c = dbm.getOneGame(gameID);
        game.setId(gameID);
        game.setDate(c.getString(1));
        game.setMaxLegs(c.getInt(4));
        game.setWinnerId(c.getInt(5));
        c.close();
        return game;
    }

    private Player getOnePlayer(DBManager dbm, int playerID) {
        Player p = new Player();
        Cursor c;
        c = dbm.getOnePLayer(playerID);
        p.setId(playerID);
        p.setName(c.getString(1));
        p.setWins(c.getInt(2));
        p.setLosses(c.getInt(3));
        c.close();
        return p;
    }

    private Leg[] getLegs(DBManager d, int g) {
        Leg[] l = {};
       // ArrayList<Leg> l = new ArrayList<Leg>();
        Cursor c;
        int num;

        c = d.getLegsForGame(g);
        num = c.getCount();
        for (int i = 0; i < num; i++) {
            //vehicles.add(new Car(...));
            l[i] = new Leg(g);
            l[i].setId(c.getInt(0));
            try {
                l[i].setWinnerID(c.getInt(2));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        c.close();

        return l;
    }

    private Turn[] getTurns(DBManager d, int leg) {
        Turn[] t = null;
        Cursor c;
        int num;

        c = d.getTurnsForLeg(leg);
        num = c.getCount();
        for (int i = 0; i < num; i++) {
            t[i] = new Turn();
            t[i].setTurnID(c.getInt(0));
            t[i].setPlayerID(c.getInt(1));
            t[i].setLegId(c.getInt(2));
            t[i].setDart(1,c.getInt(3));
            t[i].setDart(2,c.getInt(4));
            t[i].setDart(3,c.getInt(5));
        }
        c.close();
        return t;
    }
}
