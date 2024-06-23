package com.dmp.repositories.impl;

import com.dmp.pojo.ReceiptDetail;
import com.dmp.repositories.ReceiptDetailRepository;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class ReceiptDetailRepositoryImpl implements ReceiptDetailRepository {
    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<ReceiptDetail> getReceiptDetails(int receiptId) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ReceiptDetail> query = criteriaBuilder.createQuery(ReceiptDetail.class);
            Root<ReceiptDetail> rootReceiptDetail = query.from(ReceiptDetail.class);

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(rootReceiptDetail.get("receipt_id"), receiptId));

            query.where(predicates.toArray(new Predicate[0]));

            query.orderBy(criteriaBuilder.desc(rootReceiptDetail.get("amount")));

            TypedQuery<ReceiptDetail> finalQuery = session.createQuery(query);

            return finalQuery.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ReceiptDetail> getReceiptDetailsByServiceId(int serviceId) {
        try {
            Session session = this.factory.getObject().getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ReceiptDetail> query = criteriaBuilder.createQuery(ReceiptDetail.class);
            Root<ReceiptDetail> rootReceiptDetail = query.from(ReceiptDetail.class);

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(rootReceiptDetail.get("services_id"), serviceId));

            query.where(predicates.toArray(new Predicate[0]));

            TypedQuery<ReceiptDetail> finalQuery = session.createQuery(query);

            return finalQuery.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveDetailReceipt(ReceiptDetail receiptDetail) {
        Session session = this.factory.getObject().getCurrentSession();
        session.save(receiptDetail);
    }

    @Override
    public void deleteDetailReceiptById(int id) {
        Session session = this.factory.getObject().getCurrentSession();
        ReceiptDetail receiptDetail = session.get(ReceiptDetail.class, id);
        if (receiptDetail != null) {
            session.delete(receiptDetail);
        }
    }

    @Override
    public void updateDetailReceipt(ReceiptDetail receiptDetail) {
        Session session = this.factory.getObject().getCurrentSession();
        session.update(receiptDetail);
    }
}
