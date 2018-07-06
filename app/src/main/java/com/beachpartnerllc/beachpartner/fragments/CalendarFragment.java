package com.beachpartnerllc.beachpartner.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beachpartnerllc.beachpartner.EventViewModel;
import com.beachpartnerllc.beachpartner.R;
import com.beachpartnerllc.beachpartner.activity.TabActivity;
import com.beachpartnerllc.beachpartner.adpters.BenefitListItemAdapter;
import com.beachpartnerllc.beachpartner.adpters.EventsAdapter;
import com.beachpartnerllc.beachpartner.adpters.SubscriptionAdapter;
import com.beachpartnerllc.beachpartner.calendar.compactcalendarview.CompactCalendarView;
import com.beachpartnerllc.beachpartner.calendar.compactcalendarview.domain.Event;
import com.beachpartnerllc.beachpartner.connections.ApiService;
import com.beachpartnerllc.beachpartner.connections.PrefManager;
import com.beachpartnerllc.beachpartner.filter.Filter;
import com.beachpartnerllc.beachpartner.filter.FilterViewModel;
import com.beachpartnerllc.beachpartner.models.BenefitModel;
import com.beachpartnerllc.beachpartner.models.SingleSubscriptionModel;
import com.beachpartnerllc.beachpartner.models.SubscriptionItemsModels;
import com.beachpartnerllc.beachpartner.models.SubscriptonPlansModel;
import com.beachpartnerllc.beachpartner.utils.AppConstants;
import com.beachpartnerllc.beachpartner.utils.CheckPlan;
import com.beachpartnerllc.beachpartner.utils.MyJsonArrayRequest;
import com.beachpartnerllc.beachpartner.utils.SubClickInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import kotlin.Pair;

import static com.beachpartnerllc.beachpartner.utils.CheckPlan.selectedIndex;
import static com.facebook.FacebookSdk.getApplicationContext;


public class CalendarFragment extends BaseFragment implements View.OnClickListener, SubClickInterface {
	
	private static final String TAG = "CalendarFragment";
	private static boolean isMycal = false;
	public Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
	public List<Event> eventList, sList;
	public ArrayList<Event> eventModelList = new ArrayList<>();
	public List<String> eventStartDate = new ArrayList();
	public ArrayAdapter<String> adapterEventTypes, adapterYear, adapterMonths, adapterStates, adapterSubEvents, adapterRegion;
	CompactCalendarView compactCalendarView;
	SimpleDateFormat dateFormatForDisplaying = new SimpleDateFormat("dd-M-yyyy hh:mm:ss a", Locale.getDefault());
	SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
	SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
	List<String> eventTypes = new ArrayList<>();
	ArrayList<String> years = new ArrayList<String>();
	List<String> stateList = new ArrayList<>();
	List<String> regionList = new ArrayList<>();
	/*Month*/
	String[] Months = new String[]{"Please Select", "January", "February",
	    "March", "April", "May", "June", "July", "August", "September",
	    "October", "November", "December"};
	private ArrayList<Event> myeventModelList = new ArrayList<>();
	private String token, subScriptionPlan, toDayDate, usertype;
	private TextView tview_master, tview_mycalendar, tview_month, tview_date, tview_no_events;
	private ImageButton btn_previous, btn_next;
	private RecyclerView rview;
	private List<Event> toDayEvents = new ArrayList<>();
	private TabActivity tabActivity;
	private Button btnProceed, btnCancel;
	private EventsAdapter eventsAdapter;
	private SubscriptionAdapter subscriptionAdapter;
	private View layoutAlert;
	private LinearLayout topHeaderLayout;
	private TextView headerTitle, tv_price, tv_regPrice;
	private Button btnBuy;
	private RecyclerView rcview_sub_item;
	private RelativeLayout premius_header;
	private List<BenefitModel> benefitModelList = new ArrayList<>();
	private BenefitListItemAdapter benefitItemAdapter;
	private List<SubscriptonPlansModel> userPlanList = new ArrayList<>();
	private List<SubscriptonPlansModel> plansModelList = new ArrayList<>();
	private List<SubscriptionItemsModels> userSubList = new ArrayList<>();
	private List<SubscriptionItemsModels> userAddonsList = new ArrayList<>();
	private BenefitModel benefitModel;
	private CheckPlan checkPlan;
	private PrefManager prefManager;
	private Dialog filterDialogue, dialogue;
	private AlertDialog b;
	private String eventType, subType, year, month, state, region, eventType_clear;
	private Spinner spinner_events, spinner_subEvents, spinner_year, spinner_month, tv_state, tv_region;
	private FilterViewModel filterViewModel;
	private LinearLayout top_tabs, calendar_llt, eventHeader_llt, eventRecycler_llt, container_llt;
	private SubscriptonPlansModel splanModels;
	
	public CalendarFragment() {
		// Required empty public constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_calendar, container, false);
		
		token = new PrefManager(getActivity()).getToken();
		initActivity(view);
		getAllEvents();
		return view;
	}
	
	private void initActivity(View view) {
		
		tview_master = view.findViewById(R.id.txtMaster);
		tview_mycalendar = view.findViewById(R.id.txtMycalendar);
		tview_month = view.findViewById(R.id.month_name);
		tview_date = view.findViewById(R.id.tview_date);
		btn_previous = view.findViewById(R.id.prev_button);
		btn_next = view.findViewById(R.id.next_button);
		rview = view.findViewById(R.id.rcv_events);
		top_tabs = view.findViewById(R.id.top_tabs_linear_layout);
		calendar_llt = view.findViewById(R.id.calendar_whole_linear_layout);
		eventHeader_llt = view.findViewById(R.id.event_header_linear_layout);
		eventRecycler_llt = view.findViewById(R.id.event_recycler_linear_layout);
		container_llt = view.findViewById(R.id.the_container_linear_layout);
		tview_no_events = view.findViewById(R.id.no_events);
		
		compactCalendarView = view.findViewById(R.id.compactcalendar);
		toDayDate = DateFormat.getDateTimeInstance().format(new Date());
		
		compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
		compactCalendarView.setUseThreeLetterAbbreviation(true);
		
		LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		rview.setLayoutManager(manager);
		rview.setHasFixedSize(true);
		
		tview_master.setOnClickListener(this);
		tview_mycalendar.setOnClickListener(this);
		btn_next.setOnClickListener(this);
		btn_previous.setOnClickListener(this);
		
		
		AlertDialog.Builder dialogbar = new AlertDialog.Builder(getActivity());
		View holder = View.inflate(getActivity(), R.layout.progress_dialouge, null);
		dialogbar.setView(holder);
		dialogbar.setCancelable(false);
		b = dialogbar.create();
		b.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		b.setCancelable(false);
		
		//load events for today date
		activeMasterTab();
		
		
		//compact calendar onclick listener
		compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
			@Override
			public void onDayClick(Date dateClicked) {
				tview_date.setText("Events for " + dateFormat.format(dateClicked));
				toDayEvents.clear();
				List<Event> bookingsFromMap = compactCalendarView.getEvents(dateClicked);
				if (bookingsFromMap != null) {
					toDayEvents.addAll(bookingsFromMap);
					if (!usertype.equalsIgnoreCase(AppConstants.USER_TYPE_COACH) && !isMycal) {
						if (CheckPlan.isPlanAvailable(plansModelList, subScriptionPlan, AppConstants.BENIFIT_CODE_B6)) {
							checkEventShow();
						}
					} else {
						if (toDayEvents.size() != 0) {
							rview.setVisibility(View.VISIBLE);
							eventsAdapter = new EventsAdapter(getActivity(), toDayEvents, isMycal);
							rview.setAdapter(eventsAdapter);
							rview.invalidate();
							eventsAdapter.notifyDataSetChanged();
						} else {
							rview.setVisibility(View.GONE);
							tview_no_events.setVisibility(View.VISIBLE);
						}
					}
				}
			}
			
			@Override
			public void onMonthScroll(Date firstDayOfNewMonth) {
				//Log.d(TAG, "Month was scrolled to: " + firstDayOfNewMonth);
				tview_month.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
				toDayEvents.clear();
				List<Event> bookingsFromMap = compactCalendarView.getEvents(compactCalendarView.getFirstDayOfCurrentMonth());
				if (bookingsFromMap != null) {
					toDayEvents.addAll(bookingsFromMap);
					//check method to show event
					if (!usertype.equalsIgnoreCase(AppConstants.USER_TYPE_COACH) && !isMycal) {
						if (CheckPlan.isPlanAvailable(plansModelList, subScriptionPlan, AppConstants.BENIFIT_CODE_B6)) {
							checkEventShow();
						}
					} else {
						if (toDayEvents.size() != 0) {
							rview.setVisibility(View.VISIBLE);
							eventsAdapter = new EventsAdapter(getActivity(), toDayEvents, isMycal);
							rview.setAdapter(eventsAdapter);
							rview.invalidate();
							eventsAdapter.notifyDataSetChanged();
						} else {
							rview.setVisibility(View.GONE);
							tview_no_events.setVisibility(View.VISIBLE);
						}
						
					}
				}
			}
			
		});
		
		tview_month.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
		tview_date.setText("Events for " + dateFormat.format(new Date()));
		
		
	}
	
	private void checkEventShow() {
		userPlanList.clear();
		//Toast.makeText(tabActivity, "Selecetd index" + selectedIndex, Toast.LENGTH_SHORT).show();
		if (plansModelList.get(selectedIndex).getPlanName().equalsIgnoreCase(subScriptionPlan)) {
			rview.getRecycledViewPool().clear();
			if (toDayEvents.size() != 0) {
				rview.setVisibility(View.VISIBLE);
				eventsAdapter = new EventsAdapter(getActivity(), toDayEvents, isMycal);
				rview.setAdapter(eventsAdapter);
				rview.invalidate();
				eventsAdapter.notifyDataSetChanged();
			} else {
				rview.setVisibility(View.GONE);
				tview_no_events.setVisibility(View.VISIBLE);
			}
		} else {
			if (plansModelList.get(2).getPlanName().equalsIgnoreCase(subScriptionPlan)) {
				rview.getRecycledViewPool().clear();
				if (toDayEvents.size() != 0) {
					rview.setVisibility(View.VISIBLE);
					eventsAdapter = new EventsAdapter(getActivity(), toDayEvents, isMycal);
					rview.setAdapter(eventsAdapter);
					rview.invalidate();
					eventsAdapter.notifyDataSetChanged();
				} else {
					rview.setVisibility(View.GONE);
					tview_no_events.setVisibility(View.VISIBLE);
				}
			} else if (plansModelList.get(3).getPlanName().equalsIgnoreCase(subScriptionPlan)) {
				if (toDayEvents.size() != 0) {
					rview.setVisibility(View.VISIBLE);
					eventsAdapter = new EventsAdapter(getActivity(), toDayEvents, isMycal);
					rview.setAdapter(eventsAdapter);
					rview.invalidate();
					eventsAdapter.notifyDataSetChanged();
				} else {
					rview.setVisibility(View.GONE);
					tview_no_events.setVisibility(View.VISIBLE);
				}
			} else {
				showAlertDialouge();
			}
		}
		for (int i = selectedIndex; i < plansModelList.size(); i++) {
			userPlanList.add(plansModelList.get(i));
		}
	}
	
	//Show alert subscription alert dialouge
	private void showAlertDialouge() {
		
		LayoutInflater inflater = getLayoutInflater();
		//View alertLayout = inflater.inflate(R.layout.popup_no_of_likes_layout, null);//Alert dialogue previous one
		View alertLayout = inflater.inflate(R.layout.alert_subscription_layout, null);
		
		btnProceed = alertLayout.findViewById(R.id.btn_proceed);
		btnCancel = alertLayout.findViewById(R.id.btn_sub_cancel);
		RecyclerView rc_view = alertLayout.findViewById(R.id.rcv_subscribe);
		LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		rc_view.setLayoutManager(layoutManager);
		btnProceed.setClickable(false);
        /*userPlanList.clear();
        if (plansModelList.size() > 0 && plansModelList != null) {
            for (int i = 0; i <plansModelList.size() ; i++) {
                if (!plansModelList.get(i).getPlanName().equalsIgnoreCase(subScriptionPlan)) {
                    userPlanList.add(plansModelList.get(i));
                }
            }

        }*/
		
		subscriptionAdapter = new SubscriptionAdapter(getContext(), userPlanList, this, subScriptionPlan);
		rc_view.setAdapter(subscriptionAdapter);
		
		
		android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());
		// Initialize a new foreground color span instance
		ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.blueDark));
		alert.setView(alertLayout);
		alert.setCancelable(true);
		final android.app.AlertDialog dialog = alert.create();
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface arg0) {
				//dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setBackgroundColor(getResources().getColor(R.color.blueDark));
				//dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setGravity(Gravity.CENTER);
			}
		});
		dialog.show();
		btnProceed.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				if (splanModels != null) {
					subscriptionsModels(splanModels);
				}
			}
		});
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}
		});
		
	}
	
	//Method for subscrptionsModels
	private void subscriptionsModels(SubscriptonPlansModel subscriptonPlansModel) {
		LayoutInflater inflater = getLayoutInflater();
		layoutAlert = inflater.inflate(R.layout.subscription_free_layout, null);
		LinearLayoutManager linearManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
		topHeaderLayout = layoutAlert.findViewById(R.id.top_sub_header);
		headerTitle = layoutAlert.findViewById(R.id.head_sub_title);
		tv_price = layoutAlert.findViewById(R.id.tv_price);
		tv_regPrice = layoutAlert.findViewById(R.id.tv_subPrice);
		btnBuy = layoutAlert.findViewById(R.id.sub_buy);
		rcview_sub_item = layoutAlert.findViewById(R.id.rcview_sub);
		premius_header = layoutAlert.findViewById(R.id.recruit_header);
		
		rcview_sub_item.setLayoutManager(linearManager);
		//benefitModelList.clear();
		benefitModelList = subscriptonPlansModel.getBenefitList();
		if (subscriptonPlansModel.getPlanName().equalsIgnoreCase("FREE")) {
			//layoutAlert = inflater.inflate(R.layout.subscription_free_layout, null);
			tv_price.setText("$" + subscriptonPlansModel.getMonthlyCharge());
			btnBuy.setBackgroundColor(getResources().getColor(R.color.butn_free));
			if (benefitModelList != null) {
				tv_regPrice.setText("$" + subscriptonPlansModel.getRegFee());
				benefitItemAdapter = new BenefitListItemAdapter(getContext(), benefitModelList);
				rcview_sub_item.setAdapter(benefitItemAdapter);
			}
		} else if (subscriptonPlansModel.getPlanName().equalsIgnoreCase("LITE")) {
			tv_price.setText("$" + subscriptonPlansModel.getMonthlyCharge());
			topHeaderLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.header_lite));
			headerTitle.setText(R.string.lite_sub);
			btnBuy.setBackgroundColor(getResources().getColor(R.color.butn_lite));
			if (benefitModelList != null) {
				tv_regPrice.setText("$" + subscriptonPlansModel.getRegFee() + " one-time");
				benefitItemAdapter = new BenefitListItemAdapter(getContext(), benefitModelList);
				rcview_sub_item.setAdapter(benefitItemAdapter);
			}
		} else if (subscriptonPlansModel.getPlanName().equalsIgnoreCase("STANDARD")) {
			tv_price.setText("$" + subscriptonPlansModel.getMonthlyCharge());
			topHeaderLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.header_standard));
			headerTitle.setText(R.string.standard_sub);
			btnBuy.setBackgroundColor(getResources().getColor(R.color.butn_std));
			if (benefitModelList != null) {
				tv_regPrice.setText("$" + subscriptonPlansModel.getRegFee() + " one-time");
				benefitItemAdapter = new BenefitListItemAdapter(getContext(), benefitModelList);
				rcview_sub_item.setAdapter(benefitItemAdapter);
			}
		} else {
			tv_price.setText("$" + subscriptonPlansModel.getMonthlyCharge());
			topHeaderLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.header_rec));
			headerTitle.setText(R.string.recruiting_sub);
			btnBuy.setBackgroundColor(getResources().getColor(R.color.butn_recruit));
			premius_header.setVisibility(View.VISIBLE);
			if (benefitModelList != null) {
				tv_regPrice.setText("$" + subscriptonPlansModel.getRegFee() + " one-time");
				benefitItemAdapter = new BenefitListItemAdapter(getContext(), benefitModelList);
				rcview_sub_item.setAdapter(benefitItemAdapter);
			}
		}
		
		android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());
		// Initialize a new foreground color span instance
		ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.blueDark));
		alert.setView(layoutAlert);
		alert.setCancelable(true);
		final android.app.AlertDialog dialog = alert.create();
		
		dialog.show();
	}
	
	private void activeMasterTab() {
		tview_master.setTextColor(getResources().getColor(R.color.blueDark));
		tview_master.setBackgroundColor(getResources().getColor(R.color.white));
		tview_mycalendar.setBackgroundColor(0);
		tview_mycalendar.setTextColor(getResources().getColor(R.color.white));
		
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		filterViewModel = ViewModelProviders.of(this).get(FilterViewModel.class);
		EventViewModel eventVM = ViewModelProviders.of(getActivity()).get(EventViewModel.class);
		eventVM.getEvent().observe(getViewLifecycleOwner(), new Observer<Pair<Integer, Event>>() {
			@Override
			public void onChanged(@Nullable Pair<Integer, Event> eveeventPositionPair) {
				eventsAdapter.setEvent(eveeventPositionPair.getFirst(), eveeventPositionPair.getSecond());
			}
		});
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Add your menu entries here
		menu.clear();
		tabActivity.getSupportActionBar().setTitle("Calendar");
		inflater.inflate(R.menu.menu_class_fragment, menu);
		inflater.inflate(R.menu.menu_search_filter, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
			case R.id.search_filter:
				//Master calendar disable only FREE account
				if (!usertype.equalsIgnoreCase(AppConstants.USER_TYPE_COACH)) {
					if (subScriptionPlan != null) {
						if (CheckPlan.isPlanAvailable(plansModelList, subScriptionPlan, AppConstants.BENIFIT_CODE_B10)) {
							userPlanList.clear();
							//Toast.makeText(tabActivity, "Selecetd index"+selectedIndex, Toast.LENGTH_SHORT).show();
							if (plansModelList.get(selectedIndex).getPlanName().equalsIgnoreCase(subScriptionPlan)) {
								showFilterBox();
							} else {
								if (plansModelList.get(2).getPlanName().equalsIgnoreCase(subScriptionPlan)) {
									showFilterBox();
								} else if (plansModelList.get(3).getPlanName().equalsIgnoreCase(subScriptionPlan)) {
									showFilterBox();
								} else {
									showAlertDialouge();
								}
							}
							for (int i = selectedIndex; i < plansModelList.size(); i++) {
								userPlanList.add(plansModelList.get(i));
							}
						}
					}
				} else {
					showFilterBox();
				}
				break;
			default:
				break;
		}
		return false;
	}
	
	private void showFilterBox() {
		filterDialogue = new Dialog(getContext());
		//filterDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
		filterDialogue.setContentView(R.layout.popup_filter);
		filterDialogue.setCanceledOnTouchOutside(true);
		Window window = filterDialogue.getWindow();
		WindowManager.LayoutParams wlp = window.getAttributes();
		wlp.gravity = Gravity.TOP;
		wlp.y = 80;
		wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
		window.setAttributes(wlp);
		filterDialogue
		    .getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		
		filterDialogue.show();
		initView(filterDialogue);
	}
	
	private void initView(final Dialog dialogue) {
		spinner_events = dialogue.findViewById(R.id.event_spinner);
		spinner_subEvents = dialogue.findViewById(R.id.subtypes_spinner);
		spinner_year = dialogue.findViewById(R.id.year_spinner);
		spinner_month = dialogue.findViewById(R.id.month_spinner);
		final TextView clearAll = dialogue.findViewById(R.id.clear_all_txtv);
		tv_state = dialogue.findViewById(R.id.state_List);
		tv_region = dialogue.findViewById(R.id.region_list);
		
		final Button btn_search = dialogue.findViewById(R.id.btn_invite_partner);
		if (adapterEventTypes != null) {
			int eventValuePos = adapterEventTypes.getPosition(filterViewModel.getFilter().getEventType());
			spinner_events.setSelection(eventValuePos);
		}
		if (adapterSubEvents != null) {
			int eventSubValuePos = adapterSubEvents.getPosition(filterViewModel.getFilter().getSubType());
			spinner_subEvents.setSelection(eventSubValuePos);
		}
		if (adapterYear != null) {
			int yearValuePos = adapterYear.getPosition(filterViewModel.getFilter().getYear());
			spinner_year.setSelection(yearValuePos);
		}
		if (adapterMonths != null) {
			int monthValuePos = adapterMonths.getPosition(filterViewModel.getFilter().getMonth());
			spinner_month.setSelection(monthValuePos);
		}
		if (adapterStates != null) {
			int stateValuePos = adapterStates.getPosition(filterViewModel.getFilter().getState());
			tv_state.setSelection(stateValuePos);
		}
		if (adapterRegion != null) {
			int regionValuePos = adapterRegion.getPosition(filterViewModel.getFilter().getRegion());
			tv_region.setSelection(regionValuePos);
		}
		spinnerInit();
		clearAll.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				spinner_events.setAdapter(null);
				spinner_events.setAdapter(adapterEventTypes);
				spinner_month.setAdapter(null);
				spinner_month.setAdapter(adapterMonths);
				spinner_year.setAdapter(null);
				spinner_year.setAdapter(adapterYear);
				tv_state.setAdapter(null);
				tv_state.setAdapter(adapterStates);
				tv_region.setAdapter(null);
				tv_region.setAdapter(adapterRegion);
				top_tabs.setVisibility(View.VISIBLE);
				calendar_llt.setVisibility(View.VISIBLE);
				container_llt.setWeightSum((float) 4);
				LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, (float) 0.3);
				eventHeader_llt.setLayoutParams(param);
				getAllEvents();
				filterViewModel.setFilter(new Filter("", "", "", "", "", ""));
//                filterDialogue.cancel();
			
			}
		});
		
		
		btn_search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				
				eventType = spinner_events.getSelectedItem().toString();
				subType = spinner_subEvents.getSelectedItem().toString();
				year = spinner_year.getSelectedItem().toString();
				month = spinner_month.getSelectedItem().toString();
				state = tv_state.getSelectedItem().toString();
				region = tv_region.getSelectedItem().toString();
				eventType_clear = eventType.replaceAll("\\s", "");
				
				filterViewModel.setFilter(new Filter(eventType, subType, year, month, state, region));
				String searchCriteriaString = "Events for ";
				if (eventType.equalsIgnoreCase("Please Select")) {
					eventType = "";
				} else {
					searchCriteriaString = eventType + " ->";
				}
				
				if (subType.equalsIgnoreCase("Please Select") || subType.equalsIgnoreCase("Not Applicable")) {
					subType = "";
				} else {
					searchCriteriaString += subType + " ->";
				}
				if (year.equalsIgnoreCase("Please Select")) {
					year = "";
				} else {
					searchCriteriaString += year + " ->";
				}
				
				if (month.equalsIgnoreCase("Please Select")) {
					month = "";
				} else {
					searchCriteriaString += month + " ->";
				}
				
				
				if (state.equalsIgnoreCase("Please Select")) {
					state = "";
				} else {
					searchCriteriaString += state + " ->";
				}
				
				if (region.equalsIgnoreCase("Please Select")) {
					region = "";
				} else {
					searchCriteriaString += region + " ->";
				}
				
				if (month != "") {
					String dateYear = month + " " + year;
					Date startDate = null;
					DateFormat df = new SimpleDateFormat("MMMM yyyy");
					try {
						startDate = df.parse(dateYear);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					tview_month.setText(dateFormatForMonth.format(startDate));
					compactCalendarView.setCurrentDate(startDate);
				}
				
				tview_date.setText(searchCriteriaString);
				
				compactCalendarView.invalidate();
				
				JSONObject objects = new JSONObject();
				try {
					objects.put("eventType", eventType);
					objects.put("month", month);
					objects.put("region", "");
					objects.put("state", state);
					objects.put("subType", subType);
					objects.put("year", year);
					objects.put("region", region);
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				searchEvents(objects);
				dialogue.dismiss();
			}
		});
	}
	
	private void spinnerInit() {
		
		years.clear();
		eventTypes.clear();
		stateList.clear();
		regionList.clear();
		/*Events*/
		List<String> eventTypes = AppConstants.getEventTypes();
		//List for junior
		final List<String> juniorSubEvent = AppConstants.getJuniorSubEvents();
		//List For adult sub events
		final List<String> adultSubEvent = AppConstants.getAdultSubEvents();
		
		final List<String> subEventsNil = new ArrayList<>();
		subEventsNil.add("Not Applicable");
		adapterEventTypes = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, eventTypes);
		spinner_events.setAdapter(adapterEventTypes);

//        if (filterViewModel.getFilter().getEventType().equals("Junior")) {
//            adapterSubEvents = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, juniorSubEvent);
//            spinner_subEvents.setAdapter(adapterSubEvents);
//        } else if (filterViewModel.getFilter().getEventType().equals("Adult")) {
//
//            adapterSubEvents = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, adultSubEvent);
//            spinner_subEvents.setAdapter(adapterSubEvents);
//        }
//        else {
		adapterSubEvents = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subEventsNil);
		spinner_subEvents.setAdapter(adapterSubEvents);
//        }
		
		spinner_events.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				
				if (spinner_events.getSelectedItem().equals("Junior")) {
					
					adapterSubEvents = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, juniorSubEvent);
					spinner_subEvents.setAdapter(adapterSubEvents);
					
				} else if (spinner_events.getSelectedItem().equals("Adult")) {
					
					adapterSubEvents = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, adultSubEvent);
					spinner_subEvents.setAdapter(adapterSubEvents);
				} else {
					adapterSubEvents = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subEventsNil);
					spinner_subEvents.setAdapter(adapterSubEvents);
				}
				int eventSubValuePos = adapterSubEvents.getPosition(filterViewModel.getFilter().getSubType());
				spinner_subEvents.setSelection(eventSubValuePos);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
			
			}
		});
		
		
		/*Year*/
		
		int thisYear = Calendar.getInstance().get(Calendar.YEAR);
		
		for (int i = 0; i <= 3; i++) {
			years.add(Integer.toString(thisYear + i));
		}
		adapterYear = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, years);
		spinner_year.setAdapter(adapterYear);
		
		
		adapterMonths = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Months);
		spinner_month.setAdapter(adapterMonths);
		
		Typeface font = Typeface.createFromAsset(getContext().getAssets(),
		    "fonts/SanFranciscoTextRegular.ttf");
		
		/*State*/
		List<String> stateList = AppConstants.getstatelist();
		adapterStates = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, stateList);
		tv_state.setAdapter(adapterStates);
		
		//Region
		List<String> regionList = AppConstants.getregionList();
		adapterRegion = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, regionList);
		tv_region.setAdapter(adapterRegion);
	}
	
	//Api for search events
	private void searchEvents(JSONObject obj) {
		top_tabs.setVisibility(View.GONE);
		calendar_llt.setVisibility(View.GONE);
		container_llt.setWeightSum((float) 1.2);
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, (float) 0.1);
		eventHeader_llt.setLayoutParams(param);
		
		b.show();
		eventModelList.clear();
		MyJsonArrayRequest arrayRequest = new MyJsonArrayRequest(ApiService.REQUEST_METHOD_POST, ApiService.SEARCH_EVENTS, obj,
		    new Response.Listener<JSONArray>() {
			    @Override
			    public void onResponse(JSONArray response) {
				    Log.d(TAG, "onResponse Search: " + response.toString());
                    /*Type listType = new TypeToken<List<Event>>() {}.getType();
                    eventModelList = new Gson().fromJson(response.toString(), listType);
                    setupCalendar();*/
				    if (response != null) {
					    for (int i = 0; i < response.length(); i++) {
						    try {
							    
							    JSONObject object = response.getJSONObject(i);
							    
							    Event model = new Event();
							    model.setEventId(object.getString("id"));
							    model.setEventName(object.getString("eventName"));
							    model.setData(object.getString("eventDescription"));
							    model.setEventLocation(object.getString("eventLocation"));
							    model.setEventState(object.getString("state"));
							    model.setEventVenue(object.getString("eventVenue"));
							    model.setEventStartDate(object.getLong("eventStartDate"));
							    model.setEventEndDate(object.getLong("eventEndDate"));
							    model.setTimeInMillis(Long.parseLong(object.getString("eventStartDate")));
							    model.setEventRegStartdate(object.getLong("eventRegStartDate"));
							    model.setEventRegEnddate(object.getLong("eventRegEndDate"));
							    model.setEventAdmin(object.getString("eventAdmin"));
							    model.setRegType(object.getString("registerType"));
							    model.setEventTeamSizes(object.getString("teamSize"));
							    model.setUserMessage(object.getString("userMessage"));
							    model.setEventStatus(object.getString("eventStatus"));
							    model.setInvitationStatus(object.getString("invitationStatus"));
							    model.setEventUrl(object.getString("eventurl"));
							    JSONArray dateArray = object.getJSONArray("eventDates");
							    long[] mydatearray = new long[dateArray.length()];
							    if (dateArray.length() > 0) {
								    for (int j = 0; j < dateArray.length(); j++) {
									    mydatearray[j] = dateArray.getLong(j);
								    }
								    model.setEventDates(mydatearray);
							    }
							    eventModelList.add(model);
							    
						    } catch (Exception e) {
							    e.printStackTrace();
						    }
					    }
					    
				    }
				    
				    setupFilterCalendar();
			    }
		    }, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				b.cancel();
				
				String json = null;
				Log.d("error--", error.toString());
				NetworkResponse response = error.networkResponse;
				if (response != null && response.data != null) {
					switch (response.statusCode) {
						case 401:
							json = new String(response.data);
							json = trimMessage(json, "title");
							if (json != null) {
								Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
							}
							break;
						case 500:
							json = new String(response.data);
							json = trimMessage(json, "title");
							if (json != null) {
								Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
							}
							break;
						default:
							break;
					}
				}
				
			}
		}) {
			@Override
			public Map<String, String> getHeaders() {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Authorization", "Bearer " + token);
				headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
			}
		};
		if (getActivity() != null) {
			RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
			Log.d("Request", arrayRequest.toString());
			requestQueue.add(arrayRequest);
		}
	}
	
	private void setupFilterCalendar() {
		
		compactCalendarView.removeAllEvents();
		compactCalendarView.invalidate();
		if (eventModelList != null) {
			for (int i = 0; i < eventModelList.size(); i++) {
				eventStartDate.add(String.valueOf(eventModelList.get(i).getEventStartDate()));
				compactCalendarView.addEvent(eventModelList.get(i));
			}
		}
		toDayEvents.clear();
		List<Event> bookingsFromMap = compactCalendarView.getEventsForMonth(compactCalendarView.getCurrentDate());
		if (bookingsFromMap != null) {
			
			for (int i = 0; i < bookingsFromMap.size(); i++) {
				
				if (!toDayEvents.contains(bookingsFromMap.get(i))) {
					toDayEvents.add(bookingsFromMap.get(i));
				}
			}
			if (toDayEvents.size() != 0) {
				rview.setVisibility(View.VISIBLE);
				eventsAdapter = new EventsAdapter(getActivity(), toDayEvents, isMycal);
				rview.setAdapter(eventsAdapter);
				rview.invalidate();
				eventsAdapter.notifyDataSetChanged();
			} else {
				rview.setVisibility(View.GONE);
				tview_no_events.setVisibility(View.VISIBLE);
			}
			
		}
		b.cancel();
		
	}
	
	private void getAllEvents() {
		b.show();
		isMycal = false;
		eventModelList.clear();
		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_ALL_EVENTS, null,
		    new Response.Listener<JSONArray>() {
			    @Override
			    public void onResponse(JSONArray response) {
				    if (response != null) {
                            /*Log.d(TAG, "onResponse Get all Events: " + response.toString());
                            Type listType = new TypeToken<List<Event>>() {}.getType();
                            eventModelList = new Gson().fromJson(response.toString(), listType);
                            setupCalendar();*/
					    for (int i = 0; i < response.length(); i++) {
						    try {
							    
							    JSONObject object = response.getJSONObject(i);
							    
							    Event model = new Event();
							    model.setEventId(object.getString("id"));
							    model.setEventName(object.getString("eventName"));
							    model.setData(object.getString("eventDescription"));
							    model.setEventLocation(object.getString("state"));//eventlocation changed to state on 31/05/2018 6:59 pm abraham
							    model.setEventVenue(object.getString("eventVenue"));
							    model.setEventStartDate(object.getLong("eventStartDate"));
							    model.setEventEndDate(object.getLong("eventEndDate"));
							    model.setTimeInMillis(Long.parseLong(object.getString("eventStartDate")));
							    model.setEventRegStartdate(object.getLong("eventRegStartDate"));
							    model.setEventRegEnddate(object.getLong("eventRegEndDate"));
							    model.setEventAdmin(object.getString("eventAdmin"));
							    model.setEventTeamSizes(object.getString("teamSize"));
							    model.setRegType(object.getString("registerType"));
							    model.setUserMessage(object.getString("userMessage"));
							    model.setEventStatus(object.getString("eventStatus"));
							    model.setInvitationStatus(object.getString("invitationStatus"));
							    model.setEventUrl(object.getString("eventurl"));
							    JSONArray dateArray = object.getJSONArray("eventDates");
							    long[] datearray = new long[dateArray.length()];
							    if (dateArray.length() > 0) {
								    for (int j = 0; j < dateArray.length(); j++) {
									    datearray[j] = dateArray.getLong(j);
								    }
								    model.setEventDates(datearray);
							    }
							    eventModelList.add(model);
							    
						    } catch (Exception e) {
							    e.printStackTrace();
						    }
					    }
					    setupCalendar();
				    }
				    
				    
			    }
		    }, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				b.cancel();
				String json;
				Log.d("error--", error.toString());
				NetworkResponse response = error.networkResponse;
				if (response != null && response.data != null) {
					switch (response.statusCode) {
						case 401:
							json = new String(response.data);
							json = trimMessage(json, "detail");
							if (json != null) {
								Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
							}
							break;
						
						default:
							break;
					}
				}
				
			}
		}) {
			@Override
			public Map<String, String> getHeaders() {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Authorization", "Bearer " + token);
				//headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
				
			}
			
		};
		if (getActivity() != null) {
			RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
			Log.d("Request", jsonArrayRequest.toString());
			requestQueue.add(jsonArrayRequest);
			
		}
	}
	
	//set Up Master Calendar Events
	private void setupCalendar() {
		compactCalendarView.removeAllEvents();
		compactCalendarView.invalidate();
		if (eventModelList != null) {
			for (int i = 0; i < eventModelList.size(); i++) {
				eventStartDate.add(String.valueOf(eventModelList.get(i).getEventStartDate()));
				compactCalendarView.addEvent(eventModelList.get(i));
			}
		}
		b.cancel();
		currentDateEventSetter();
	}
	
	private void currentDateEventSetter() {
		if (!usertype.equalsIgnoreCase(AppConstants.USER_TYPE_COACH)) {
			if (subScriptionPlan != null && !subScriptionPlan.equalsIgnoreCase(AppConstants.SUBSCIPTION_TYPE_ONE)) {
				showDefaultEvents();
			}
		} else {
			showDefaultEvents();
		}
	}
	
	private void showDefaultEvents() {
		if (dateFormat.format(compactCalendarView.getCurrentDate()).equalsIgnoreCase(dateFormat.format(new Date()))) {
			toDayEvents.clear();
			List<Event> bookingsFromMap = compactCalendarView.getEvents(compactCalendarView.getCurrentDate());
			if (bookingsFromMap != null) {
				toDayEvents.addAll(bookingsFromMap);
				if (toDayEvents.size() != 0) {
					rview.setVisibility(View.VISIBLE);
					eventsAdapter = new EventsAdapter(getActivity(), toDayEvents, isMycal);
					rview.setAdapter(eventsAdapter);
					rview.invalidate();
					eventsAdapter.notifyDataSetChanged();
					tview_date.setText("Events for " + dateFormat.format(new Date()));
				} else {
					rview.setVisibility(View.GONE);
					tview_no_events.setVisibility(View.VISIBLE);
				}
			}
			
		}
	}
	
	private String trimMessage(String json, String detail) {
		String trimmedString = null;
		
		try {
			JSONObject obj = new JSONObject(json);
			trimmedString = obj.getString(detail);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
		return trimmedString;
	}
	
	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (getActivity() instanceof TabActivity) {
			tabActivity = (TabActivity) getActivity();
			tabActivity.setActionBarTitle("Calendar");
			tabActivity.disableFloatButtons();
		}
		
		prefManager = new PrefManager(getActivity());
		token = prefManager.getToken();
		subScriptionPlan = prefManager.getSubscriptionType();
		usertype = prefManager.getUserType();
		getuserSubscriptions();
	}
	
	private void getuserSubscriptions() {
		JsonObjectRequest request = new JsonObjectRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_USER_ACTIVE_PLANS, null, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				if (response != null) {
					Log.d(TAG, "onUserPlansResponse: ");
					SingleSubscriptionModel subscriptionModel = new Gson().fromJson(response.toString(), SingleSubscriptionModel.class);
					userSubList = subscriptionModel.getSubItemModel();
					userAddonsList = subscriptionModel.getAddonsItemModel();
					saveSubscription();
					getSubscriptionPlans();//Information for all subscriptions
					
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
			
			}
		}) {
			@Override
			public Map<String, String> getHeaders() {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Authorization", "Bearer " + token);
				//headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
			}
		};
		if (getActivity() != null) {
			RequestQueue queue = Volley.newRequestQueue(getActivity());
			Log.d("SubscriptionRequest", queue.toString());
			queue.add(request);
		}
		
	}
	
	private void saveSubscription() {
		
		if (userSubList != null && userSubList.size() > 0) {
			for (int i = 0; i < userSubList.size(); i++) {
				if (getActivity() != null) {
					new PrefManager(getContext()).saveSubscription(userSubList.get(i).getPlanName(), userSubList.get(i).getRemainingDays());
				}
			}
			
		}
	}
	
	//Get all subscriptions
	private void getSubscriptionPlans() {
		plansModelList.clear();
		JsonArrayRequest arrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_SUBSCRIPTION_PLANS, null, new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				if (response != null) {
					Log.d(TAG, "onResponse: " + response.toString());
					Type type = new TypeToken<List<SubscriptonPlansModel>>() {
					}.getType();
					plansModelList = new Gson().fromJson(response.toString(), type);
				}
				
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
			
			}
		}) {
			@Override
			public Map<String, String> getHeaders() {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Authorization", "Bearer " + token);
				//headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
				
			}
			
		};
		if (getActivity() != null) {
			RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
			Log.d("SubscriptionRequest", arrayRequest.toString());
			requestQueue.add(arrayRequest);
		}
		
	}
	
	@Override
	public void onClick(View view) {
		
		switch (view.getId()) {
			case R.id.txtMaster:
				tabActivity.setActionBarTitle("Master Calendar");
				compactCalendarView.removeAllEvents();
				compactCalendarView.invalidate();
				setHasOptionsMenu(true); //filter needed in master calendar
				if (eventsAdapter != null) {
					eventsAdapter.notifyDataSetChanged();
				}
				activeMasterTab();
				toDayEvents.clear();
				getAllEvents();
				isMycal = false;
				break;
			
			case R.id.txtMycalendar:
				tabActivity.setActionBarTitle("My Calendar");
				setHasOptionsMenu(false);
				compactCalendarView.removeAllEvents();
				compactCalendarView.invalidate();
				if (eventsAdapter != null) {
					eventsAdapter.notifyDataSetChanged();
				}
				activeMycalendarTab();
				toDayEvents.clear();
				getMycalendarEvents();
				isMycal = true;
				break;
			
			case R.id.next_button:
				compactCalendarView.showNextMonth();
				break;
			
			case R.id.prev_button:
				compactCalendarView.showPreviousMonth();
				break;
			
			default:
				break;
		}
	}
	
	private void activeMycalendarTab() {
		tview_mycalendar.setTextColor(getResources().getColor(R.color.blueDark));
		tview_mycalendar.setBackgroundColor(getResources().getColor(R.color.white));
		tview_master.setBackgroundColor(0);
		tview_master.setTextColor(getResources().getColor(R.color.white));
		
	}
	
	//Get MyCalendar Events
	private void getMycalendarEvents() {
		b.show();
		myeventModelList.clear();
		final String userId = new PrefManager(getApplicationContext()).getUserId();
		JsonArrayRequest arrayRequest = new JsonArrayRequest(ApiService.REQUEST_METHOD_GET, ApiService.GET_USER_EVENTS + userId, null,
		    new Response.Listener<JSONArray>() {
			    @Override
			    public void onResponse(JSONArray response) {
				    if (response != null) {
					    for (int i = 0; i < response.length(); i++) {
						    
						    try {
							    
							    JSONObject obj = response.getJSONObject(i);
							    
							    JSONObject jsonObject = obj.getJSONObject("event");
							    Event eventModel = new Event();
							    eventModel.setEventId(jsonObject.getString("id"));
							    eventModel.setEventName(jsonObject.getString("eventName"));
							    eventModel.setData(jsonObject.getString("eventDescription"));
							    eventModel.setEventState(jsonObject.getString("state")); //changed location to state
							    eventModel.setEventVenue(jsonObject.getString("eventVenue"));
							    eventModel.setEventStartDate(jsonObject.getLong("eventStartDate"));
							    eventModel.setEventTeamSizes(jsonObject.getString("teamSize"));
							    eventModel.setTimeInMillis(Long.parseLong(jsonObject.getString("eventStartDate")));
							    eventModel.setEventEndDate(jsonObject.getLong("eventEndDate"));
							    eventModel.setEventAdmin(jsonObject.getString("eventAdmin"));
							    JSONArray dateArray = obj.getJSONArray("eventDates");
							    long[] mydatearray = new long[dateArray.length()];
							    if (dateArray.length() > 0) {
								    for (int j = 0; j < dateArray.length(); j++) {
									    mydatearray[j] = dateArray.getLong(j);
								    }
								    eventModel.setEventDates(mydatearray);
							    }
							    myeventModelList.add(eventModel);
						    } catch (JSONException e) {
							    e.printStackTrace();
						    }
					    }
					    setupMyCalendar();
					    
				    }
			    }
		    }, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				b.cancel();
				String json = null;
				Log.d("error--", error.toString());
				NetworkResponse response = error.networkResponse;
				if (response != null && response.data != null) {
					switch (response.statusCode) {
						case 401:
							json = new String(response.data);
							json = trimMessage(json, "detail");
							if (json != null) {
								Toast.makeText(getActivity(), "" + json, Toast.LENGTH_LONG).show();
							}
							break;
						
						default:
							break;
					}
				}
			}
		}) {
			@Override
			public Map<String, String> getHeaders() {
				HashMap<String, String> headers = new HashMap<String, String>();
				headers.put("Authorization", "Bearer " + token);
				//headers.put("Content-Type", "application/json; charset=utf-8");
				return headers;
				
			}
		};
		if (getActivity() != null) {
			RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
			Log.d("Request", arrayRequest.toString());
			requestQueue.add(arrayRequest);
		}
	}
	
	//Method for setUp MyCalendar Events
	private void setupMyCalendar() {
		compactCalendarView.removeAllEvents();
		compactCalendarView.invalidate();
		if (myeventModelList != null) {
			for (int i = 0; i < myeventModelList.size(); i++) {
				compactCalendarView.addEvent(myeventModelList.get(i));
			}
		}
		b.cancel();
		currentDateEventSetter();
		
	}
	
	@Override
	public void changeViews(SubscriptonPlansModel plansModel) {
		btnProceed.setBackgroundColor(getResources().getColor(R.color.btn_sub));
		btnProceed.setTextColor(getResources().getColor(R.color.white));
		splanModels = plansModel;
		
	}
	
}
