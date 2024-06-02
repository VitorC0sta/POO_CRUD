/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.controllers;

import biblioteca.controllers.exceptions.IllegalOrphanException;
import biblioteca.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import biblioteca.entities.LocalizacaoFisica;
import biblioteca.entities.Emprestimo;
import java.util.ArrayList;
import java.util.List;
import biblioteca.entities.ListaEspera;
import biblioteca.entities.Livro;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author vitor
 */
public class LivroJpaController implements Serializable {

    public LivroJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Livro livro) {
        if (livro.getEmprestimoList() == null) {
            livro.setEmprestimoList(new ArrayList<Emprestimo>());
        }
        if (livro.getListaEsperaList() == null) {
            livro.setListaEsperaList(new ArrayList<ListaEspera>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LocalizacaoFisica IDLocalizacao = livro.getIDLocalizacao();
            if (IDLocalizacao != null) {
                IDLocalizacao = em.getReference(IDLocalizacao.getClass(), IDLocalizacao.getIDLocalizacao());
                livro.setIDLocalizacao(IDLocalizacao);
            }
            List<Emprestimo> attachedEmprestimoList = new ArrayList<Emprestimo>();
            for (Emprestimo emprestimoListEmprestimoToAttach : livro.getEmprestimoList()) {
                emprestimoListEmprestimoToAttach = em.getReference(emprestimoListEmprestimoToAttach.getClass(), emprestimoListEmprestimoToAttach.getIDEmprestimo());
                attachedEmprestimoList.add(emprestimoListEmprestimoToAttach);
            }
            livro.setEmprestimoList(attachedEmprestimoList);
            List<ListaEspera> attachedListaEsperaList = new ArrayList<ListaEspera>();
            for (ListaEspera listaEsperaListListaEsperaToAttach : livro.getListaEsperaList()) {
                listaEsperaListListaEsperaToAttach = em.getReference(listaEsperaListListaEsperaToAttach.getClass(), listaEsperaListListaEsperaToAttach.getIDLista());
                attachedListaEsperaList.add(listaEsperaListListaEsperaToAttach);
            }
            livro.setListaEsperaList(attachedListaEsperaList);
            em.persist(livro);
            if (IDLocalizacao != null) {
                IDLocalizacao.getLivroList().add(livro);
                IDLocalizacao = em.merge(IDLocalizacao);
            }
            for (Emprestimo emprestimoListEmprestimo : livro.getEmprestimoList()) {
                Livro oldIDLivroOfEmprestimoListEmprestimo = emprestimoListEmprestimo.getIDLivro();
                emprestimoListEmprestimo.setIDLivro(livro);
                emprestimoListEmprestimo = em.merge(emprestimoListEmprestimo);
                if (oldIDLivroOfEmprestimoListEmprestimo != null) {
                    oldIDLivroOfEmprestimoListEmprestimo.getEmprestimoList().remove(emprestimoListEmprestimo);
                    oldIDLivroOfEmprestimoListEmprestimo = em.merge(oldIDLivroOfEmprestimoListEmprestimo);
                }
            }
            for (ListaEspera listaEsperaListListaEspera : livro.getListaEsperaList()) {
                Livro oldIDLivroOfListaEsperaListListaEspera = listaEsperaListListaEspera.getIDLivro();
                listaEsperaListListaEspera.setIDLivro(livro);
                listaEsperaListListaEspera = em.merge(listaEsperaListListaEspera);
                if (oldIDLivroOfListaEsperaListListaEspera != null) {
                    oldIDLivroOfListaEsperaListListaEspera.getListaEsperaList().remove(listaEsperaListListaEspera);
                    oldIDLivroOfListaEsperaListListaEspera = em.merge(oldIDLivroOfListaEsperaListListaEspera);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Livro livro) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Livro persistentLivro = em.find(Livro.class, livro.getIDLivro());
            LocalizacaoFisica IDLocalizacaoOld = persistentLivro.getIDLocalizacao();
            LocalizacaoFisica IDLocalizacaoNew = livro.getIDLocalizacao();
            List<Emprestimo> emprestimoListOld = persistentLivro.getEmprestimoList();
            List<Emprestimo> emprestimoListNew = livro.getEmprestimoList();
            List<ListaEspera> listaEsperaListOld = persistentLivro.getListaEsperaList();
            List<ListaEspera> listaEsperaListNew = livro.getListaEsperaList();
            List<String> illegalOrphanMessages = null;
            for (Emprestimo emprestimoListOldEmprestimo : emprestimoListOld) {
                if (!emprestimoListNew.contains(emprestimoListOldEmprestimo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Emprestimo " + emprestimoListOldEmprestimo + " since its IDLivro field is not nullable.");
                }
            }
            for (ListaEspera listaEsperaListOldListaEspera : listaEsperaListOld) {
                if (!listaEsperaListNew.contains(listaEsperaListOldListaEspera)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ListaEspera " + listaEsperaListOldListaEspera + " since its IDLivro field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (IDLocalizacaoNew != null) {
                IDLocalizacaoNew = em.getReference(IDLocalizacaoNew.getClass(), IDLocalizacaoNew.getIDLocalizacao());
                livro.setIDLocalizacao(IDLocalizacaoNew);
            }
            List<Emprestimo> attachedEmprestimoListNew = new ArrayList<Emprestimo>();
            for (Emprestimo emprestimoListNewEmprestimoToAttach : emprestimoListNew) {
                emprestimoListNewEmprestimoToAttach = em.getReference(emprestimoListNewEmprestimoToAttach.getClass(), emprestimoListNewEmprestimoToAttach.getIDEmprestimo());
                attachedEmprestimoListNew.add(emprestimoListNewEmprestimoToAttach);
            }
            emprestimoListNew = attachedEmprestimoListNew;
            livro.setEmprestimoList(emprestimoListNew);
            List<ListaEspera> attachedListaEsperaListNew = new ArrayList<ListaEspera>();
            for (ListaEspera listaEsperaListNewListaEsperaToAttach : listaEsperaListNew) {
                listaEsperaListNewListaEsperaToAttach = em.getReference(listaEsperaListNewListaEsperaToAttach.getClass(), listaEsperaListNewListaEsperaToAttach.getIDLista());
                attachedListaEsperaListNew.add(listaEsperaListNewListaEsperaToAttach);
            }
            listaEsperaListNew = attachedListaEsperaListNew;
            livro.setListaEsperaList(listaEsperaListNew);
            livro = em.merge(livro);
            if (IDLocalizacaoOld != null && !IDLocalizacaoOld.equals(IDLocalizacaoNew)) {
                IDLocalizacaoOld.getLivroList().remove(livro);
                IDLocalizacaoOld = em.merge(IDLocalizacaoOld);
            }
            if (IDLocalizacaoNew != null && !IDLocalizacaoNew.equals(IDLocalizacaoOld)) {
                IDLocalizacaoNew.getLivroList().add(livro);
                IDLocalizacaoNew = em.merge(IDLocalizacaoNew);
            }
            for (Emprestimo emprestimoListNewEmprestimo : emprestimoListNew) {
                if (!emprestimoListOld.contains(emprestimoListNewEmprestimo)) {
                    Livro oldIDLivroOfEmprestimoListNewEmprestimo = emprestimoListNewEmprestimo.getIDLivro();
                    emprestimoListNewEmprestimo.setIDLivro(livro);
                    emprestimoListNewEmprestimo = em.merge(emprestimoListNewEmprestimo);
                    if (oldIDLivroOfEmprestimoListNewEmprestimo != null && !oldIDLivroOfEmprestimoListNewEmprestimo.equals(livro)) {
                        oldIDLivroOfEmprestimoListNewEmprestimo.getEmprestimoList().remove(emprestimoListNewEmprestimo);
                        oldIDLivroOfEmprestimoListNewEmprestimo = em.merge(oldIDLivroOfEmprestimoListNewEmprestimo);
                    }
                }
            }
            for (ListaEspera listaEsperaListNewListaEspera : listaEsperaListNew) {
                if (!listaEsperaListOld.contains(listaEsperaListNewListaEspera)) {
                    Livro oldIDLivroOfListaEsperaListNewListaEspera = listaEsperaListNewListaEspera.getIDLivro();
                    listaEsperaListNewListaEspera.setIDLivro(livro);
                    listaEsperaListNewListaEspera = em.merge(listaEsperaListNewListaEspera);
                    if (oldIDLivroOfListaEsperaListNewListaEspera != null && !oldIDLivroOfListaEsperaListNewListaEspera.equals(livro)) {
                        oldIDLivroOfListaEsperaListNewListaEspera.getListaEsperaList().remove(listaEsperaListNewListaEspera);
                        oldIDLivroOfListaEsperaListNewListaEspera = em.merge(oldIDLivroOfListaEsperaListNewListaEspera);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = livro.getIDLivro();
                if (findLivro(id) == null) {
                    throw new NonexistentEntityException("The livro with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Livro livro;
            try {
                livro = em.getReference(Livro.class, id);
                livro.getIDLivro();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The livro with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Emprestimo> emprestimoListOrphanCheck = livro.getEmprestimoList();
            for (Emprestimo emprestimoListOrphanCheckEmprestimo : emprestimoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Livro (" + livro + ") cannot be destroyed since the Emprestimo " + emprestimoListOrphanCheckEmprestimo + " in its emprestimoList field has a non-nullable IDLivro field.");
            }
            List<ListaEspera> listaEsperaListOrphanCheck = livro.getListaEsperaList();
            for (ListaEspera listaEsperaListOrphanCheckListaEspera : listaEsperaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Livro (" + livro + ") cannot be destroyed since the ListaEspera " + listaEsperaListOrphanCheckListaEspera + " in its listaEsperaList field has a non-nullable IDLivro field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            LocalizacaoFisica IDLocalizacao = livro.getIDLocalizacao();
            if (IDLocalizacao != null) {
                IDLocalizacao.getLivroList().remove(livro);
                IDLocalizacao = em.merge(IDLocalizacao);
            }
            em.remove(livro);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Livro> findLivroEntities() {
        return findLivroEntities(true, -1, -1);
    }

    public List<Livro> findLivroEntities(int maxResults, int firstResult) {
        return findLivroEntities(false, maxResults, firstResult);
    }

    private List<Livro> findLivroEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Livro.class));
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

    public Livro findLivro(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Livro.class, id);
        } finally {
            em.close();
        }
    }

    public int getLivroCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Livro> rt = cq.from(Livro.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
