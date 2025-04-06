package com.example.flo.databinding;
import com.example.flo.R;
import com.example.flo.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentAlbumBindingImpl extends FragmentAlbumBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.album_like_iv, 1);
        sViewsWithIds.put(R.id.album_more_iv, 2);
        sViewsWithIds.put(R.id.album_lp_iv, 3);
        sViewsWithIds.put(R.id.album_back_iv, 4);
        sViewsWithIds.put(R.id.album_music_title_tv, 5);
        sViewsWithIds.put(R.id.album_singer_name_tv, 6);
        sViewsWithIds.put(R.id.album_music_title_info_tv, 7);
        sViewsWithIds.put(R.id.album_album_iv, 8);
        sViewsWithIds.put(R.id.linearLayout4, 9);
        sViewsWithIds.put(R.id.album_line_view, 10);
        sViewsWithIds.put(R.id.song_mix_layout, 11);
        sViewsWithIds.put(R.id.song_mix_tv, 12);
        sViewsWithIds.put(R.id.song_mixoff_tg, 13);
        sViewsWithIds.put(R.id.song_mixon_tg, 14);
        sViewsWithIds.put(R.id.song_all_select_iv, 15);
        sViewsWithIds.put(R.id.song_all_select_tv, 16);
        sViewsWithIds.put(R.id.song_all_listen_iv, 17);
        sViewsWithIds.put(R.id.song_all_listen_tv, 18);
        sViewsWithIds.put(R.id.song_music_list_layout, 19);
        sViewsWithIds.put(R.id.song_lalac_layout, 20);
        sViewsWithIds.put(R.id.song_list_order_01_tv, 21);
        sViewsWithIds.put(R.id.song_list_title_01_tv, 22);
        sViewsWithIds.put(R.id.song_music_title_01_tv, 23);
        sViewsWithIds.put(R.id.song_singer_name_01_tv, 24);
        sViewsWithIds.put(R.id.song_play_01_iv, 25);
        sViewsWithIds.put(R.id.song_more_01_iv, 26);
        sViewsWithIds.put(R.id.song_flu_layout, 27);
        sViewsWithIds.put(R.id.song_list_order_02_tv, 28);
        sViewsWithIds.put(R.id.song_music_title_02_tv, 29);
        sViewsWithIds.put(R.id.song_singer_name_02_tv, 30);
        sViewsWithIds.put(R.id.song_play_02_iv, 31);
        sViewsWithIds.put(R.id.song_more_02_iv, 32);
        sViewsWithIds.put(R.id.song_coin_layout, 33);
        sViewsWithIds.put(R.id.song_list_order_03_tv, 34);
        sViewsWithIds.put(R.id.song_list_title_03_tv, 35);
        sViewsWithIds.put(R.id.song_music_title_03_tv, 36);
        sViewsWithIds.put(R.id.song_singer_name_03_tv, 37);
        sViewsWithIds.put(R.id.song_play_03_iv, 38);
        sViewsWithIds.put(R.id.song_more_03_iv, 39);
        sViewsWithIds.put(R.id.song_spring_layout, 40);
        sViewsWithIds.put(R.id.song_list_order_04_tv, 41);
        sViewsWithIds.put(R.id.song_music_title_04_tv, 42);
        sViewsWithIds.put(R.id.song_singer_name_04_tv, 43);
        sViewsWithIds.put(R.id.song_play_04_iv, 44);
        sViewsWithIds.put(R.id.song_more_04_iv, 45);
        sViewsWithIds.put(R.id.song_celebrity_layout, 46);
        sViewsWithIds.put(R.id.song_list_order_05_tv, 47);
        sViewsWithIds.put(R.id.song_music_title_05_tv, 48);
        sViewsWithIds.put(R.id.song_singer_name_05_tv, 49);
        sViewsWithIds.put(R.id.song_play_05_iv, 50);
        sViewsWithIds.put(R.id.song_more_05_iv, 51);
        sViewsWithIds.put(R.id.song_sing_layout, 52);
        sViewsWithIds.put(R.id.song_list_order_06_tv, 53);
        sViewsWithIds.put(R.id.song_music_title_06_tv, 54);
        sViewsWithIds.put(R.id.song_singer_name_06_tv, 55);
        sViewsWithIds.put(R.id.song_play_06_iv, 56);
        sViewsWithIds.put(R.id.song_more_06_iv, 57);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentAlbumBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 58, sIncludes, sViewsWithIds));
    }
    private FragmentAlbumBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[8]
            , (android.widget.ImageView) bindings[4]
            , (android.widget.ImageView) bindings[1]
            , (android.view.View) bindings[10]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.ImageView) bindings[2]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[6]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.ImageView) bindings[17]
            , (android.widget.TextView) bindings[18]
            , (android.widget.ImageView) bindings[15]
            , (android.widget.TextView) bindings[16]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[46]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[33]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[27]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[20]
            , (android.widget.TextView) bindings[21]
            , (android.widget.TextView) bindings[28]
            , (android.widget.TextView) bindings[34]
            , (android.widget.TextView) bindings[41]
            , (android.widget.TextView) bindings[47]
            , (android.widget.TextView) bindings[53]
            , (android.widget.TextView) bindings[22]
            , (android.widget.TextView) bindings[35]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.TextView) bindings[12]
            , (android.widget.ImageView) bindings[13]
            , (android.widget.ImageView) bindings[14]
            , (android.widget.ImageView) bindings[26]
            , (android.widget.ImageView) bindings[32]
            , (android.widget.ImageView) bindings[39]
            , (android.widget.ImageView) bindings[45]
            , (android.widget.ImageView) bindings[51]
            , (android.widget.ImageView) bindings[57]
            , (android.widget.LinearLayout) bindings[19]
            , (android.widget.TextView) bindings[23]
            , (android.widget.TextView) bindings[29]
            , (android.widget.TextView) bindings[36]
            , (android.widget.TextView) bindings[42]
            , (android.widget.TextView) bindings[48]
            , (android.widget.TextView) bindings[54]
            , (android.widget.ImageView) bindings[25]
            , (android.widget.ImageView) bindings[31]
            , (android.widget.ImageView) bindings[38]
            , (android.widget.ImageView) bindings[44]
            , (android.widget.ImageView) bindings[50]
            , (android.widget.ImageView) bindings[56]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[52]
            , (android.widget.TextView) bindings[24]
            , (android.widget.TextView) bindings[30]
            , (android.widget.TextView) bindings[37]
            , (android.widget.TextView) bindings[43]
            , (android.widget.TextView) bindings[49]
            , (android.widget.TextView) bindings[55]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[40]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}