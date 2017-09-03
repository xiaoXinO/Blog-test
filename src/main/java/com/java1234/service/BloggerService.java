package com.java1234.service;

import com.java1234.entity.Blogger;

/**
 * ����Service�ӿ�
 *
 * @author Administrator
 */
public interface BloggerService {

    /**
     * ͨ���û�����ѯ�û�
     *
     * @param userName
     * @return
     */
    public Blogger getByUserName(String userName);

    /**
     * ��ѯ������Ϣ
     *
     * @return
     */
    public Blogger find();

    /**
     * �޸Ĳ�����Ϣ
     *
     * @param blogger
     * @return
     */
    Integer update(Blogger blogger);
}
