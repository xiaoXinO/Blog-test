package com.java1234.dao;

import com.java1234.entity.Blogger;

/**
 * ����Dao�ӿ�
 *
 * @author Administrator
 */
public interface BloggerDao {

    /**
     * ͨ���û�����ѯ�û�
     *
     * @param userName
     * @return
     */
    Blogger getByUserName(String userName);

    /**
     * ��ѯ������Ϣ
     *
     * @return
     */
    Blogger find();

    /**
     * �޸Ĳ�����Ϣ
     *
     * @param blogger
     * @return
     */
    Integer update(Blogger blogger);
}
