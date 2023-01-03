package com.alex.amazingtalker_recruit_android

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alex.amazingtalker_recruit_android.adapters.ScheduleTimeListAdapter
import com.alex.amazingtalker_recruit_android.databinding.FragmentScheduleBinding
import com.alex.amazingtalker_recruit_android.utilities.InjectorUtils
import com.alex.amazingtalker_recruit_android.viewmodels.ScheduleViewModel
import com.alex.amazingtalker_recruit_android.viewmodels.WeekAction
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ScheduleFragment : Fragment() {

    private var binding: FragmentScheduleBinding? = null
    private val viewModel: ScheduleViewModel by viewModels {
        InjectorUtils.provideScheduleViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentScheduleBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            val adapter = ScheduleTimeListAdapter()
            scheduleTimeRecyclerview.adapter = adapter
            subscribeUi(this, adapter)

            tablayout.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    Log.e(TAG, "======onTabSelected====" + tab.tag)
                    //                        Handler().postDelayed(Runnable { tablayout.getTabAt(5)?.select() }, 100)
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    Log.e(TAG, "======onTabUnselected====" + tab.tag)
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                    Log.e(TAG, "======onTabReselected====" + tab.tag)
                }
            })

            setHasOptionsMenu(true)
        }
    }

    private fun subscribeUi(binding: FragmentScheduleBinding, adapter: ScheduleTimeListAdapter) {
        viewModel.weekMondayLocalDate.observe(viewLifecycleOwner) {
            if (it < OffsetDateTime.now()) {
                binding.buttonLastWeek.colorFilter = null
                binding.buttonLastWeek.setOnClickListener(null)
            } else {
                binding.buttonLastWeek.setColorFilter(resources.getColor(R.color.amazingtalker_green_900))
                binding.buttonLastWeek.setOnClickListener(View.OnClickListener {
                    viewModel.updateWeek(WeekAction.ACTION_LAST_WEEK)
                })
            }
        }

        viewModel.weekSundayLocalDate.observe(viewLifecycleOwner) {
            binding.buttonNextWeek.setColorFilter(resources.getColor(R.color.amazingtalker_green_900))
            binding.buttonNextWeek.setOnClickListener(View.OnClickListener {
                viewModel.updateWeek(WeekAction.ACTION_NEXT_WEEK)
            })
        }

        viewModel.weekLocalDateText.observe(viewLifecycleOwner) {
            binding.textWeek.text = it
        }

        viewModel.dateTabStringList.observe(viewLifecycleOwner) { options ->
            binding.tablayout.doOnLayout {
                val tabWidth = binding.tablayout.width / 3
                val tabHeight = binding.tablayout.height
                binding.tablayout.removeAllTabs()
                options.forEachIndexed { _, element ->
                    binding.tablayout.newTab().run {
                        var elementToLocalTime = element.atZoneSameInstant(ZoneId.systemDefault()).toOffsetDateTime()
                        setCustomView(R.layout.custom_tab)
                        customView?.minimumWidth = tabWidth
                        customView?.minimumHeight = tabHeight
                        tag = elementToLocalTime.toString()
                        val dateFormatter = DateTimeFormatter.ofPattern("E, MMM dd")
                        text = dateFormatter.format(elementToLocalTime)
                        binding.tablayout.addTab(this)
                    }
                }
            }
        }

        viewModel.currentSearchResult.observe(viewLifecycleOwner) {
            adapter.submitList(it.bookeds)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}