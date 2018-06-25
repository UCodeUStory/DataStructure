package com.wangpos.datastructure.interview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.view.View;

import com.wangpos.datastructure.R;

/**
 * Created by qiyue on 2018/6/20.
 */

public class DesignV28Activity extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_designv28);

        final Chip chip = (Chip)findViewById(R.id.single_chip);

        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chip.setVisibility(View.INVISIBLE);
            }
        });

        ChipGroup chipGroup = (ChipGroup)findViewById(R.id.chipGroup);
        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                group.setVisibility(View.INVISIBLE);
            }
        });


    }
}
