package ru.mgusev.easyredminetimer.app.navigation;

import androidx.fragment.app.Fragment;


import ru.mgusev.easyredminetimer.app.ui.main.MainFragment;
import ru.mgusev.easyredminetimer.app.ui.project_list.ProjectListFragment;
import ru.mgusev.easyredminetimer.app.ui.report.ReportFragment;
import ru.mgusev.easyredminetimer.app.ui.request_token.RequestTokenFragment;
import ru.mgusev.easyredminetimer.app.ui.selected_project_list.SelectedProjectListFragment;
import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

    public static final class MainScreen extends SupportAppScreen {

        @Override
        public Fragment getFragment() {
            return MainFragment.getInstance();
        }
    }

    public static final class RequestTokenScreen extends SupportAppScreen {

        @Override
        public Fragment getFragment() {
            return RequestTokenFragment.getInstance();
        }
    }

    public static final class ProjectListScreen extends SupportAppScreen {

        @Override
        public Fragment getFragment() {
            return ProjectListFragment.getInstance();
        }
    }

    public static final class SelectedProjectListScreen extends SupportAppScreen {

        @Override
        public Fragment getFragment() {
            return SelectedProjectListFragment.getInstance();
        }
    }

    public static final class ReportScreen extends SupportAppScreen {

        @Override
        public Fragment getFragment() {
            return ReportFragment.getInstance();
        }
    }

//    public static final class TabScreen extends SupportAppScreen {
//
//        private final int position;
//        private final Category podcastsCategory;
//
//        public TabScreen(int position, Category podcastsCategory) {
//            this.position = position;
//            this.podcastsCategory = podcastsCategory;
//        }
//
//        @Override
//        public Fragment getFragment() {
//            switch (position) {
//                case MainFragment.TAB_NEWS:
//                    return NewsPagerFragment.getInstance();
//                case MainFragment.TAB_PODCASTS:
//                    return PodcastListFragment.getInstance(podcastsCategory);
//                case MainFragment.TAB_BOOKMARKS:
//                    return BookmarkListFragment.getInstance();
//                default:
//                    return new NewsListFragment();
//            }
//        }
//    }
//
//    public static final class NewsDetailsScreen extends SupportAppScreen {
//
//        private final String url;
//
//        public NewsDetailsScreen(String url) {
//            this.url = url;
//        }
//
//        @Override
//        public Fragment getFragment() {
//            return NewsDetailsFragment.getInstance(url);
//        }
//    }
}
