/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.controllers;

import biblioteca.controllers.exceptions.NonexistentEntityException;
import biblioteca.entities.ListaEspera;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import biblioteca.entities.Livro;
import biblioteca.entities.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author vitor
 */
public class ListaEsperaJpaController implements Serializable {

    public ListaEsperaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ListaEspera listaEspera) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Livro IDLivro = listaEspera.getIDLivro();
            if (IDLivro != null) {
                IDLivro = em.getReference(IDLivro.getClass(), IDLivro.getIDLivro());
                listaEspera.setIDLivro(IDLivro);
            }
            Usuario IDUsuario = listaEspera.getIDUsuario();
            if (IDUsuario != null) {
                IDUsuario = em.getReference(IDUsuario.getClass(), IDUsuario.getIDUsuario());
                listaEspera.setIDUsuario(IDUsuario);
            }
            em.persist(listaEspera);
            if (IDLivro != null) {
                IDLivro.getListaEsperaList().add(listaEspera);
                IDLivro = em.merge(IDLivro);
            }
            if (IDUsuario != null) {
                IDUsuario.getListaEsperaList().add(listaEspera);
                IDUsuario = em.merge(IDUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ListaEspera listaEspera) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ListaEspera persistentListaEspera = em.find(ListaEspera.class, listaEspera.getIDLista());
            Livro IDLivroOld = persistentListaEspera.getIDLivro();
            Livro IDLivroNew = listaEspera.getIDLivro();
            Usuario IDUsuarioOld = persistentListaEspera.getIDUsuario();
            Usuario IDUsuarioNew = listaEspera.getIDUsuario();
            if (IDLivroNew != null) {
                IDLivroNew = em.getReference(IDLivroNew.getClass(), IDLivroNew.getIDLivro());
                listaEspera.setIDLivro(IDLivroNew);
            }
            if (IDUsuarioNew != null) {
                IDUsuarioNew = em.getReference(IDUsuarioNew.getClass(), IDUsuarioNew.getIDUsuario());
                listaEspera.setIDUsuario(IDUsuarioNew);
            }
            listaEspera = em.merge(listaEspera);
            if (IDLivroOld != null && !IDLivroOld.equals(IDLivroNew)) {
                IDLivroOld.getListaEsperaList().remove(listaEspera);
                IDLivroOld = em.merge(IDLivroOld);
            }
            if (IDLivroNew != null && !IDLivroNew.equals(IDLivroOld)) {
                IDLivroNew.getListaEsperaList().add(listaEspera);
                IDLivroNew = em.merge(IDLivroNew);
            }
            if (IDUsuarioOld != null && !IDUsuarioOld.equals(IDUsuarioNew)) {
                IDUsuarioOld.getListaEsperaList().remove(listaEspera);
                IDUsuarioOld = em.merge(IDUsuarioOld);
            }
            if (IDUsuarioNew != null && !IDUsuarioNew.equals(IDUsuarioOld)) {
                IDUsuarioNew.getListaEsperaList().add(listaEspera);
                IDUsuarioNew = em.merge(IDUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = listaEspera.getIDLista();
                if (findListaEspera(id) == null) {
                    throw new NonexistentEntityException("The listaEspera with id " + id + " no longer exists.");
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
            ListaEspera listaEspera;
            try {
                listaEspera = em.getReference(ListaEspera.class, id);
                listaEspera.getIDLista();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The listaEspera with id " + id + " no longer exists.", enfe);
            }
            Livro IDLivro = listaEspera.getIDLivro();
            if (IDLivro != null) {
                IDLivro.getListaEsperaList().remove(listaEspera);
                IDLivro = em.merge(IDLivro);
            }
            Usuario IDUsuario = listaEspera.getIDUsuario();
            if (IDUsuario != null) {
                IDUsuario.getListaEsperaList().remove(listaEspera);
                IDUsuario = em.merge(IDUsuario);
            }
            em.remove(listaEspera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ListaEspera> findListaEsperaEntities() {
        return findListaEsperaEntities(true, -1, -1);
    }

    public List<ListaEspera> findListaEsperaEntities(int maxResults, int firstResult) {
        return findListaEsperaEntities(false, maxResults, firstResult);
    }

    private List<ListaEspera> findListaEsperaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ListaEspera.class));
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

    public ListaEspera findListaEspera(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ListaEspera.class, id);
        } finally {
            em.close();
        }
    }

    public int getListaEsperaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ListaEspera> rt = cq.from(ListaEspera.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
