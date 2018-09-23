package endorphine.icampyou;

import android.app.Fragment;
import android.app.FragmentManager;

/**
 * 프래그먼트끼리 이동이 수월하도록 기능을 넣은 클래스
 * 프래그먼트 클래스들은 무조건 이 BaseFragment클래스를 상속받아서 사용
 */
public class BaseFragment extends Fragment {

    protected void startFragment(FragmentManager fm, Class<? extends BaseFragment> fragmentClass) {
        BaseFragment fragment = null;
        try {
            fragment = fragmentClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (fragment == null) {
            throw new IllegalStateException("cannot start fragment. " + fragmentClass.getName());
        }
        getActivity().getFragmentManager().beginTransaction().add(R.id.main_frame, fragment).addToBackStack(null).commit();
    }

    protected void finishFragment() {
        getFragmentManager().popBackStack();
    }

    public void onPressedBackkey() {
        finishFragment();
    }
}
