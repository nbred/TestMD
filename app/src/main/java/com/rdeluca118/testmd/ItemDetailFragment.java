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
        View rootView = inflater.inflate(R.layout.my_item_layout_2, container, false);

        Leg[] legs = null;
        Turn[] turnArray = null;
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

            Player1 = getAPlayer(dbm, p1ID);
            game.setPlayer1(Player1);
            Player2 = getAPlayer(dbm, p2ID);
            game.setPlayer2(Player2);

            int winnerID = game.getWinnerId();
            if (winnerID == p1ID) {
                winner = Player1;
                looser = Player2;
            } else {
                winner = Player2;
                looser = Player1;
            }
           int ii = 0;
            legs = getLegs(dbm, game.getId());
            for (Leg i : legs) {
                ii++;
                turnArray = getTurns(dbm, i.getLegId(),ii);
            }

            dbm.close();

            Spanned pane1 = Html.fromHtml("Win : <h2>" + winner.getName() + "</h2><br><p>Record:</p><p><h4>" + winner.getWins() + " W --- " + winner.getLosses() + " L</h4></p>");
            ((TextView) rootView.findViewById(R.id.winner_detail)).setText(pane1);
            Spanned pane2 = Html.fromHtml("Loss : <h2>" + looser.getName() + "</h2><br><p>Record:</p><p><h4>" + looser.getWins() + " W --- " + looser.getLosses() + " L</h4></p>");
            ((TextView) rootView.findViewById(R.id.looser_details)).setText(pane2);

            TextView wText = rootView.findViewById(R.id.win_turn_text);
            wText.setText("");
            TextView lText = rootView.findViewById(R.id.loose_turn_text);
            lText.setText("");
            for (Turn x : turnArray) {
                if (x.getPlayerId() == winnerID) {
                    wText.append(makeTurnString(x));
                } else {
                    lText.append(makeTurnString(x));
                }
            }
        }
        // Show the dummy content as text in a TextView.

        return rootView;
    }
    private String makeTurnString(Turn turn){
        String str;
        int[] d;
        d = turn.getDarts();
        str = "Leg: " + turn.getGameLeg() + " = " + d[0] + " - " + d[1] + " - " + d[2] + '\n';
        return str;
    }

    private Game getTheGame(DBManager dbm, int gameID) {
        Player emptyPlayer = new Player();
        Game game = new Game(emptyPlayer, emptyPlayer, 0);
        Cursor c = dbm.getOneGame(gameID);
        if (c == null) {
            return null;
        }
        game.setId(gameID);
        game.setDate(c.getString(1));
        game.setMaxLegs(c.getInt(4));
        game.setWinnerId(c.getInt(5));
        c.close();
        return game;
    }

    private Player getAPlayer(DBManager dbm, int playerID) {
        Player p = new Player();
        Cursor c;
        c = dbm.getOnePLayer(playerID);
        if (c == null) {
            return null;
        }
        p.setId(playerID);
        p.setName(c.getString(1));
        p.setWins(c.getInt(2));
        p.setLosses(c.getInt(3));
        c.close();
        return p;
    }

    private Leg[] getLegs(DBManager d, int g) {
        Cursor c;
        int num;

        c = d.getLegsForGame(g);
        if (c == null) {
            return null;
        }
        num = c.getCount();
        Leg[] legarray = new Leg[num];

        for (int i = 0; i < num; i++) {
            legarray[i] = new Leg(g);
            legarray[i].setId(c.getInt(0));
            legarray[i].setWinnerID(c.getInt(2));
            c.moveToNext();
        }

        c.close();

        return legarray;
    }

    private Turn[] getTurns(DBManager d, int leg, int gameLeg) {
        Cursor c;
        int num;

        c = d.getTurnsForLeg(leg);
        if (c == null) {
            return null;
        }
        num = c.getCount();
        Turn[] turnarray = new Turn[num];
        for (int i = 0; i < num; i++) {
            turnarray[i] = new Turn();
            turnarray[i].setTurnID(c.getInt(0));
            turnarray[i].setPlayerID(c.getInt(1));
            turnarray[i].setLegId(c.getInt(2));
            turnarray[i].setDart(1, c.getInt(3));
            turnarray[i].setDart(2, c.getInt(4));
            turnarray[i].setDart(3, c.getInt(5));
            turnarray[i].setGameLeg(gameLeg);
            c.moveToNext();
        }
        c.close();
        return turnarray;
    }
}
