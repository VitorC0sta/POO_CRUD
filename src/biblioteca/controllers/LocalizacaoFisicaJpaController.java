/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.controllers;

import biblioteca.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import biblioteca.entities.Livro;
import biblioteca.entities.LocalizacaoFisica;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author vitor
 */
public class LocalizacaoFisicaJpaController implements Serializable {

    public LocalizacaoFisicaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LocalizacaoFisica localizacaoFisica) {
        if (localizacaoFisica.getLivroList() == null) {
            localizacaoFisica.setLivroList(new ArrayList<Livro>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Livro> attachedLivroList = new ArrayList<Livro>();
            for (Livro livroListLivroToAttach : localizacaoFisica.getLivroList()) {
                livroListLivroToAttach = em.getReference(livroListLivroToAttach.getClass(), livroListLivroToAttach.getIDLivro());
                attachedLivroList.add(livroListLivroToAttach);
            }
            localizacaoFisica.setLivroList(attachedLivroList);
            em.persist(localizacaoFisica);
            for (Livro livroListLivro : localizacaoFisica.getLivroList()) {
                LocalizacaoFisica oldIDLocalizacaoOfLivroListLivro = livroListLivro.getIDLocalizacao();
                livroListLivro.setIDLocalizacao(localizacaoFisica);
                livroListLivro = em.merge(livroListLivro);
                if (oldIDLocalizacaoOfLivroListLivro != null) {
                    oldIDLocalizacaoOfLivroListLivro.getLivroList().remove(livroListLivro);
                    oldIDLocalizacaoOfLivroListLivro = em.merge(oldIDLocalizacaoOfLivroListLivro);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LocalizacaoFisica localizacaoFisica) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LocalizacaoFisica persistentLocalizacaoFisica = em.find(LocalizacaoFisica.class, localizacaoFisica.getIDLocalizacao());
            List<Livro> livroListOld = persistentLocalizacaoFisica.getLivroList();
            List<Livro> livroListNew = localizacaoFisica.getLivroList();
            List<Livro> attachedLivroListNew = new ArrayList<Livro>();
            for (Livro livroListNewLivroToAttach : livroListNew) {
                livroListNewLivroToAttach = em.getReference(livroListNewLivroToAttach.getClass(), livroListNewLivroToAttach.getIDLivro());
                attachedLivroListNew.add(livroListNewLivroToAttach);
            }
            livroListNew = attachedLivroListNew;
            localizacaoFisica.setLivroList(livroListNew);
            localizacaoFisica = em.merge(localizacaoFisica);
            for (Livro livroListOldLivro : livroListOld) {
                if (!livroListNew.contains(livroListOldLivro)) {
                    livroListOldLivro.setIDLocalizacao(null);
                    livroListOldLivro = em.merge(livroListOldLivro);
                }
            }
            for (Livro livroListNewLivro : livroListNew) {
                if (!livroListOld.contains(livroListNewLivro)) {
                    LocalizacaoFisica oldIDLocalizacaoOfLivroListNewLivro = livroListNewLivro.getIDLocalizacao();
                    livroListNewLivro.setIDLocalizacao(localizacaoFisica);
                    livroListNewLivro = em.merge(livroListNewLivro);
                    if (oldIDLocalizacaoOfLivroListNewLivro != null && !oldIDLocalizacaoOfLivroListNewLivro.equals(localizacaoFisica)) {
                        oldIDLocalizacaoOfLivroListNewLivro.getLivroList().remove(livroListNewLivro);
                        oldIDLocalizacaoOfLivroListNewLivro = em.merge(oldIDLocalizacaoOfLivroListNewLivro);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = localizacaoFisica.getIDLocalizacao();
                if (findLocalizacaoFisica(id) == null) {
                    throw new NonexistentEntityException("The localizacaoFisica with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LocalizacaoFisica localizacaoFisica;
            try {
                localizacaoFisica = em.getReference(LocalizacaoFisica.class, id);
                localizacaoFisica.getIDLocalizacao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The localizacaoFisica with id " + id + " no longer exists.", enfe);
            }
            List<Livro> livroList = localizacaoFisica.getLivroList();
            for (Livro livroListLivro : livroList) {
                livroListLivro.setIDLocalizacao(null);
                livroListLivro = em.merge(livroListLivro);
            }
            em.remove(localizacaoFisica);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LocalizacaoFisica> findLocalizacaoFisicaEntities() {
        return findLocalizacaoFisicaEntities(true, -1, -1);
    }

    public List<LocalizacaoFisica> findLocalizacaoFisicaEntities(int maxResults, int firstResult) {
        return findLocalizacaoFisicaEntities(false, maxResults, firstResult);
    }

    private List<LocalizacaoFisica> findLocalizacaoFisicaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LocalizacaoFisica.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public LocalizacaoFisica findLocalizacaoFisica(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LocalizacaoFisica.class, id);
        } finally {
            em.close();
        }
    }

    public int getLocalizacaoFisicaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LocalizacaoFisica> rt = cq.from(LocalizacaoFisica.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
