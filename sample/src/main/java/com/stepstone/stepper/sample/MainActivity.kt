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

package com.stepstone.stepper.sample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import java.util.*

class MainActivity : AppCompatActivity() {

    @BindView(R.id.list)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        setSupportActionBar(toolbar)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SampleItemAdapter()
    }

    internal inner class SampleItemAdapter internal constructor() : RecyclerView.Adapter<SampleItemViewHolder>() {

        internal val items: List<SampleItem> = Arrays.asList(
                SampleItem(getString(R.string.default_dots), getString(R.string.default_dots_description), DefaultDotsActivity::class.java),
                SampleItem(getString(R.string.styled_dots), getString(R.string.styled_dots_description), StyledDotsActivity::class.java),
                SampleItem(getString(R.string.themed_dots), getString(R.string.themed_dots_description), ThemedDotsActivity::class.java),
                SampleItem(getString(R.string.default_progress_bar), getString(R.string.default_progress_bar_description), DefaultProgressBarActivity::class.java),
                SampleItem(getString(R.string.styled_progress_bar), getString(R.string.styled_progress_bar_description), StyledProgressBarActivity::class.java),
                SampleItem(getString(R.string.default_tabs), getString(R.string.default_tabs_description), DefaultTabsActivity::class.java),
                SampleItem(getString(R.string.styled_tabs), getString(R.string.styled_tabs_description), StyledTabsActivity::class.java),
                SampleItem(getString(R.string.error_tabs), getString(R.string.error_tabs_description), ShowErrorTabActivity::class.java),
                SampleItem(getString(R.string.error_color_tabs), getString(R.string.error_color_tabs_description), ShowErrorCustomColorTabActivity::class.java),
                SampleItem(getString(R.string.error_back_tabs), getString(R.string.error_back_tabs_description), ShowErrorOnBackTabActivity::class.java),
                SampleItem(getString(R.string.combination), getString(R.string.combination_description), CombinationActivity::class.java),
                SampleItem(getString(R.string.custom_page_transformer), getString(R.string.custom_page_transformer_description), CustomPageTransformerActivity::class.java),
                SampleItem(getString(R.string.delayed_transition), getString(R.string.delayed_transition_description), DelayedTransitionStepperActivity::class.java),
                SampleItem(getString(R.string.stepper_feedback), getString(R.string.stepper_feedback_description), StepperFeedbackActivity::class.java),
                SampleItem(getString(R.string.custom_navigation_buttons), getString(R.string.custom_navigation_buttons_description), CustomNavigationButtonsActivity::class.java),
                SampleItem(getString(R.string.show_back_button), getString(R.string.show_back_button_description), ReturnButtonActivity::class.java),
                SampleItem(getString(R.string.no_fragments), getString(R.string.no_fragments_description), NoFragmentsActivity::class.java),
                SampleItem(getString(R.string.proceed_programmatically), getString(R.string.proceed_programmatically_description), ProceedProgrammaticallyActivity::class.java),
                SampleItem(getString(R.string.passing_data_between_steps), getString(R.string.passing_data_between_steps_description), PassDataBetweenStepsActivity::class.java),
                SampleItem(getString(R.string.disabled_tab_navigation), getString(R.string.disabled_tab_navigation_description), DisabledTabNavigationActivity::class.java),
                SampleItem(getString(R.string.custom_stepperlayout_theme), getString(R.string.custom_stepperlayout_theme_description), CustomStepperLayoutThemeActivity::class.java)
        )

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sample_info, parent, false)
            return SampleItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: SampleItemViewHolder, position: Int) {
            holder.bindItem(items[position])
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItemCount(): Int {
            return items.size
        }
    }

    internal class SampleItemViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.item_sample_title)
        lateinit var title: TextView

        @BindView(R.id.item_sample_subtitle)
        lateinit var subtitle: TextView

        lateinit var item: SampleItem

        init {
            ButterKnife.bind(this, itemView)
        }

        internal fun bindItem(item: SampleItem) {
            this.item = item
            title.text = item.title
            subtitle.text = item.subtitle
        }

        @OnClick
        internal fun onClick(v: View) {
            val context = v.context
            context.startActivity(Intent(context, item.activityClass))
        }
    }

    internal class SampleItem internal constructor(
            internal val title: String,
            internal val subtitle: String,
            internal val activityClass: Class<*>)

}
