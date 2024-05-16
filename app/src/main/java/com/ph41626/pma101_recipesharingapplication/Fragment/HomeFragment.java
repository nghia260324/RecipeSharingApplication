package com.ph41626.pma101_recipesharingapplication.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ph41626.pma101_recipesharingapplication.Activity.MainActivity;
import com.ph41626.pma101_recipesharingapplication.Adapter.RecyclerViewRecipeTrendingAdapter;
import com.ph41626.pma101_recipesharingapplication.Model.Recipe;
import com.ph41626.pma101_recipesharingapplication.Model.ViewModel;
import com.ph41626.pma101_recipesharingapplication.R;
import com.ph41626.pma101_recipesharingapplication.Services.FirebaseUtils;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView rcv_trending;
    private RecyclerViewRecipeTrendingAdapter recipeTrendingAdapter;

    private ArrayList<Recipe> recipes = new ArrayList<>();

    private ViewModel viewModel;
    private FirebaseUtils firebaseUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initUI(view);
        RecyclerViewManager();
        GetDataFromFirebase();
        UpdateUiWhenDataChange();

        return view;
    }

    private void UpdateUiWhenDataChange() {
        viewModel.getChangeDataRecipes().observe(getViewLifecycleOwner(), new Observer<ArrayList<Recipe>>() {
            @Override
            public void onChanged(ArrayList<Recipe> recipes) {
                recipeTrendingAdapter.Update(recipes);
            }
        });
    }

    private void GetDataFromFirebase() {
        firebaseUtils.getDataFromFirebase(MainActivity.REALTIME_RECIPES, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recipes.clear();
                for (DataSnapshot recipeSnapshot:snapshot.getChildren()) {
                    Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                    recipes.add(recipe);
                }
                viewModel.changeDateRecipes(recipes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void RecyclerViewManager() {
        recipeTrendingAdapter = new RecyclerViewRecipeTrendingAdapter(getContext(),recipes);
        rcv_trending.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        rcv_trending.setAdapter(recipeTrendingAdapter);
    }

    private void initUI(View view) {
        rcv_trending = view.findViewById(R.id.rcv_trending);

        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        firebaseUtils = new FirebaseUtils();
    }
}