package me.minutz.rwmanager.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.minutz.rwmanager.API;
import me.minutz.rwmanager.R;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(inflater.getContext());
        recyclerView.setLayoutManager(layoutManager);


        API.useri.observe(getViewLifecycleOwner(), new Observer<ArrayList<JSONObject>>() {
            @Override
            public void onChanged(ArrayList<JSONObject> jsonObjects) {
                UserListAdaptor mAdapter = new UserListAdaptor(jsonObjects);
                recyclerView.setAdapter(mAdapter);
            }
        });
        return root;
    }

    public class UserListAdaptor extends RecyclerView.Adapter<UserListAdaptor.UserCardHolder> {
        private ArrayList<JSONObject> dataset;

        public class UserCardHolder extends RecyclerView.ViewHolder {
            public TextView textView;
            public UserCardHolder(View view) {
                super(view);
                this.textView = view.findViewById(R.id.textViewNume);
            }
        }

        public UserListAdaptor(ArrayList<JSONObject> dataset) {
            this.dataset = dataset;
        }

        @Override
        public UserListAdaptor.UserCardHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_card, parent, false);
            UserCardHolder vh = new UserCardHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(UserCardHolder holder, int position) {
            try {
                holder.textView.setText(dataset.get(position).getString("nume"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return dataset.size();
        }
    }
}