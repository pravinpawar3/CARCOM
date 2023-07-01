package com.project.carcom.park;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.carcom.R;
import com.project.carcom.databinding.FragmentCarParkBinding;

/**
 *  CarParkFragment class provides options related to parked car.
 */
public class CarParkFragment extends Fragment {

    private FragmentCarParkBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCarParkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button buttonLockCar = root.findViewById(R.id.Park);
        buttonLockCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Carcom/prav/Park");
                databaseRef.setValue(0);
            }
        });
        Button buttonUnLockCar = root.findViewById(R.id.Map);
        buttonUnLockCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(container.getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}