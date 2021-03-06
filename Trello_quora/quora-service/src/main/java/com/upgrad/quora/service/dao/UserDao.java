package com.upgrad.quora.service.dao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

@Repository
public class UserDao implements Serializable {

    @PersistenceContext
    private EntityManager entityManager;

    //Create record in User table.
    public UserEntity createUser(UserEntity userEntity){
        entityManager.persist(userEntity);
        return userEntity;
    }

    //Get user record using username.
    public UserEntity getUserByUserName(final String userName) {
        try {
            return entityManager.createNamedQuery("userByUserName", UserEntity.class).setParameter("userName", userName).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    //Get user record using email
    public UserEntity getUserByEmail(final String email) {
        try {
            return entityManager.createNamedQuery("userByEmail", UserEntity.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public void updateUser(final UserEntity updatedUserEntity) {
        entityManager.merge(updatedUserEntity);
    }

//    get user by userid
    public UserEntity getUser(final String userUuid){
        try {
            return entityManager.createNamedQuery("userByUuid", UserEntity.class)
                    .setParameter("uuid", userUuid)
                    .getSingleResult();
        }catch (NoResultException nre){
            return null;
        }
    }
    public UserAuthTokenEntity getUserAuthToken(final String authorizationToken) {
        try {
            return entityManager.createNamedQuery("userAuthTokenByAccessToken", UserAuthTokenEntity.class)
                    .setParameter("accessToken", authorizationToken)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public UserEntity deleteUser(final String userId) {
        UserEntity deleteUser = getUser(userId);
        if (deleteUser != null) {
            this.entityManager.remove(deleteUser);
        }
        return deleteUser;
    }

}
