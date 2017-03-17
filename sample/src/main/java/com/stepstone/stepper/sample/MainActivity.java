/*
Copyright 2016 StepStone Services

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.stepstone.stepper.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.list)
    RecyclerView recyclerView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SampleItemAdapter());
    }

    private class SampleItemAdapter extends RecyclerView.Adapter<SampleItemViewHolder> {

        @NonNull
        private List<SampleItem> items;

        private SampleItemAdapter() {
            this.items = Arrays.asList(
                    new SampleItem(getString(R.string.default_dots), getString(R.string.default_dots_description), DefaultDotsActivity.class),
                    new SampleItem(getString(R.string.styled_dots), getString(R.string.styled_dots_description), StyledDotsActivity.class),
                    new SampleItem(getString(R.string.themed_dots), getString(R.string.themed_dots_description), ThemedDotsActivity.class),
                    new SampleItem(getString(R.string.default_progress_bar), getString(R.string.default_progress_bar_description), DefaultProgressBarActivity.class),
                    new SampleItem(getString(R.string.styled_progress_bar), getString(R.string.styled_progress_bar_description), StyledProgressBarActivity.class),
                    new SampleItem(getString(R.string.default_tabs), getString(R.string.default_tabs_description), DefaultTabsActivity.class),
                    new SampleItem(getString(R.string.styled_tabs), getString(R.string.styled_tabs_description), StyledTabsActivity.class),
                    new SampleItem(getString(R.string.error_tabs), getString(R.string.error_tabs_description), ShowErrorTabActivity.class),
                    new SampleItem(getString(R.string.error_color_tabs), getString(R.string.error_color_tabs_description), ShowErrorCustomColorTabActivity.class),
                    new SampleItem(getString(R.string.error_back_tabs), getString(R.string.error_back_tabs_description), ShowErrorOnBackTabActivity.class),
                    new SampleItem(getString(R.string.combination), getString(R.string.combination_description), CombinationActivity.class),
                    new SampleItem(getString(R.string.custom_page_transformer), getString(R.string.custom_page_transformer_description), CustomPageTransformerActivity.class),
                    new SampleItem(getString(R.string.delayed_transition), getString(R.string.delayed_transition_description), DelayedTransitionStepperActivity.class),
                    new SampleItem(getString(R.string.stepper_feedback), getString(R.string.stepper_feedback_description), StepperFeedbackActivity.class),
                    new SampleItem(getString(R.string.custom_navigation_buttons), getString(R.string.custom_navigation_buttons_description), CustomNavigationButtonsActivity.class),
                    new SampleItem(getString(R.string.show_back_button), getString(R.string.show_back_button_description), ReturnButtonActivity.class),
                    new SampleItem(getString(R.string.no_fragments), getString(R.string.no_fragments_description), NoFragmentsActivity.class),
                    new SampleItem(getString(R.string.proceed_programmatically), getString(R.string.proceed_programmatically_description), ProceedProgrammaticallyActivity.class),
                    new SampleItem(getString(R.string.disabled_tab_navigation), getString(R.string.disabled_tab_navigation_description), DisabledTabNavigationActivity.class),
                    new SampleItem(getString(R.string.custom_stepperlayout_theme), getString(R.string.custom_stepperlayout_theme_description), CustomStepperLayoutThemeActivity.class)
            );
        }

        @Override
        public SampleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sample_info, parent, false);
            return new SampleItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SampleItemViewHolder holder, int position) {
            holder.bindItem(items.get(position));
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    static class SampleItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.item_sample_title)
        TextView title;

        @Bind(R.id.item_sample_subtitle)
        TextView subtitle;

        private SampleItem item;

        private SampleItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        private void bindItem(SampleItem item) {
            this.item = item;
            title.setText(item.title);
            subtitle.setText(item.subtitle);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            context.startActivity(new Intent(context, item.activityClass));
        }
    }

    private static class SampleItem {

        private String title;

        private String subtitle;

        private Class<?> activityClass;

        private SampleItem(String title, String subtitle, Class<?> activityClass) {
            this.title = title;
            this.subtitle = subtitle;
            this.activityClass = activityClass;
        }
    }

}
