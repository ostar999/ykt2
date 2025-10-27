package com.hyphenate.easeui.modules.contact.presenter;

import android.text.TextUtils;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.provider.EaseUserProfileProvider;
import com.hyphenate.exceptions.HyphenateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseContactPresenterImpl extends EaseContactPresenter {
    private void checkUserProvider(List<EaseUser> list) {
        EaseUserProfileProvider userProvider = EaseIM.getInstance().getUserProvider();
        if (userProvider != null) {
            for (EaseUser easeUser : list) {
                EaseUser user = userProvider.getUser(easeUser.getUsername());
                if (user != null) {
                    if (TextUtils.isEmpty(easeUser.getNickname()) || TextUtils.equals(easeUser.getNickname(), easeUser.getUsername())) {
                        easeUser.setNickname(user.getNickname());
                    }
                    if (TextUtils.isEmpty(easeUser.getAvatar())) {
                        easeUser.setAvatar(user.getAvatar());
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addNote$5(int i2) {
        IEaseContactListView iEaseContactListView = this.mView;
        iEaseContactListView.addNoteFail(i2, iEaseContactListView.context().getString(R.string.ease_contact_add_note_developing));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadData$0() {
        this.mView.loadContactListNoData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadData$1(List list) {
        if (isDestroy()) {
            return;
        }
        this.mView.loadContactListSuccess(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadData$2(HyphenateException hyphenateException) {
        this.mView.loadContactListFail(hyphenateException.getDescription());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadData$3() {
        List<String> blackListFromServer;
        try {
            List allContactsFromServer = EMClient.getInstance().contactManager().getAllContactsFromServer();
            List<String> selfIdsOnOtherPlatform = EMClient.getInstance().contactManager().getSelfIdsOnOtherPlatform();
            if ((allContactsFromServer != null && !allContactsFromServer.isEmpty()) || (selfIdsOnOtherPlatform != null && !selfIdsOnOtherPlatform.isEmpty())) {
                if (allContactsFromServer == null) {
                    allContactsFromServer = new ArrayList();
                }
                if (selfIdsOnOtherPlatform != null && !selfIdsOnOtherPlatform.isEmpty()) {
                    allContactsFromServer.addAll(selfIdsOnOtherPlatform);
                }
                final List<EaseUser> list = EaseUser.parse((List<String>) allContactsFromServer);
                if (list != null && !list.isEmpty() && (blackListFromServer = EMClient.getInstance().contactManager().getBlackListFromServer()) != null && !blackListFromServer.isEmpty()) {
                    for (EaseUser easeUser : list) {
                        if (!blackListFromServer.isEmpty() && blackListFromServer.contains(easeUser.getUsername())) {
                            easeUser.setContact(1);
                        }
                    }
                }
                runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.contact.presenter.d
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f8729c.lambda$loadData$1(list);
                    }
                });
                return;
            }
            if (isDestroy()) {
                return;
            }
            runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.contact.presenter.c
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8728c.lambda$loadData$0();
                }
            });
        } catch (HyphenateException e2) {
            e2.printStackTrace();
            if (isDestroy()) {
                return;
            }
            runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.contact.presenter.e
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8731c.lambda$loadData$2(e2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sortData$4(List list) {
        this.mView.sortContactListSuccess(list);
    }

    private void sortList(List<EaseUser> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        Collections.sort(list, new Comparator<EaseUser>() { // from class: com.hyphenate.easeui.modules.contact.presenter.EaseContactPresenterImpl.1
            @Override // java.util.Comparator
            public int compare(EaseUser easeUser, EaseUser easeUser2) {
                if (easeUser.getInitialLetter().equals(easeUser2.getInitialLetter())) {
                    return easeUser.getNickname().compareTo(easeUser2.getNickname());
                }
                if (DictionaryFactory.SHARP.equals(easeUser.getInitialLetter())) {
                    return 1;
                }
                if (DictionaryFactory.SHARP.equals(easeUser2.getInitialLetter())) {
                    return -1;
                }
                return easeUser.getInitialLetter().compareTo(easeUser2.getInitialLetter());
            }
        });
    }

    @Override // com.hyphenate.easeui.modules.contact.presenter.EaseContactPresenter
    public void addNote(final int i2, EaseUser easeUser) {
        if (isDestroy()) {
            return;
        }
        runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.contact.presenter.a
            @Override // java.lang.Runnable
            public final void run() {
                this.f8724c.lambda$addNote$5(i2);
            }
        });
    }

    @Override // com.hyphenate.easeui.modules.contact.presenter.EaseContactPresenter
    public void loadData() {
        runOnIO(new Runnable() { // from class: com.hyphenate.easeui.modules.contact.presenter.f
            @Override // java.lang.Runnable
            public final void run() {
                this.f8733c.lambda$loadData$3();
            }
        });
    }

    @Override // com.hyphenate.easeui.modules.contact.presenter.EaseContactPresenter
    public void sortData(final List<EaseUser> list) {
        if (list != null) {
            checkUserProvider(list);
            sortList(list);
            if (isDestroy()) {
                return;
            }
            runOnUI(new Runnable() { // from class: com.hyphenate.easeui.modules.contact.presenter.b
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8726c.lambda$sortData$4(list);
                }
            });
        }
    }
}
