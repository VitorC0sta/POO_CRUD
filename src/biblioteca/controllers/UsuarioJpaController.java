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
import biblioteca.entities.Emprestimo;
import java.util.ArrayList;
import java.util.List;
import biblioteca.entities.ListaEspera;
import biblioteca.entities.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author vitor
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getEmprestimoList() == null) {
            usuario.setEmprestimoList(new ArrayList<Emprestimo>());
        }
        if (usuario.getListaEsperaList() == null) {
            usuario.setListaEsperaList(new ArrayList<ListaEspera>());
        }
        
        EntityManager em = null;
        
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Emprestimo> attachedEmprestimoList = new ArrayList<Emprestimo>();
            for (Emprestimo emprestimoListEmprestimoToAttach : usuario.getEmprestimoList()) {
                emprestimoListEmprestimoToAttach = em.getReference(emprestimoListEmprestimoToAttach.getClass(), emprestimoListEmprestimoToAttach.getIDEmprestimo());
                attachedEmprestimoList.add(emprestimoListEmprestimoToAttach);
            }
            usuario.setEmprestimoList(attachedEmprestimoList);
            List<ListaEspera> attachedListaEsperaList = new ArrayList<ListaEspera>();
            for (ListaEspera listaEsperaListListaEsperaToAttach : usuario.getListaEsperaList()) {
                listaEsperaListListaEsperaToAttach = em.getReference(listaEsperaListListaEsperaToAttach.getClass(), listaEsperaListListaEsperaToAttach.getIDLista());
                attachedListaEsperaList.add(listaEsperaListListaEsperaToAttach);
            }
            usuario.setListaEsperaList(attachedListaEsperaList);
            em.persist(usuario);
            for (Emprestimo emprestimoListEmprestimo : usuario.getEmprestimoList()) {
                Usuario oldIDUsuarioOfEmprestimoListEmprestimo = emprestimoListEmprestimo.getIDUsuario();
                emprestimoListEmprestimo.setIDUsuario(usuario);
                emprestimoListEmprestimo = em.merge(emprestimoListEmprestimo);
                if (oldIDUsuarioOfEmprestimoListEmprestimo != null) {
                    oldIDUsuarioOfEmprestimoListEmprestimo.getEmprestimoList().remove(emprestimoListEmprestimo);
                    oldIDUsuarioOfEmprestimoListEmprestimo = em.merge(oldIDUsuarioOfEmprestimoListEmprestimo);
                }
            }
            for (ListaEspera listaEsperaListListaEspera : usuario.getListaEsperaList()) {
                Usuario oldIDUsuarioOfListaEsperaListListaEspera = listaEsperaListListaEspera.getIDUsuario();
                listaEsperaListListaEspera.setIDUsuario(usuario);
                listaEsperaListListaEspera = em.merge(listaEsperaListListaEspera);
                if (oldIDUsuarioOfListaEsperaListListaEspera != null) {
                    oldIDUsuarioOfListaEsperaListListaEspera.getListaEsperaList().remove(listaEsperaListListaEspera);
                    oldIDUsuarioOfListaEsperaListListaEspera = em.merge(oldIDUsuarioOfListaEsperaListListaEspera);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIDUsuario());
            List<Emprestimo> emprestimoListOld = persistentUsuario.getEmprestimoList();
            List<Emprestimo> emprestimoListNew = usuario.getEmprestimoList();
            List<ListaEspera> listaEsperaListOld = persistentUsuario.getListaEsperaList();
            List<ListaEspera> listaEsperaListNew = usuario.getListaEsperaList();
            List<String> illegalOrphanMessages = null;
            for (Emprestimo emprestimoListOldEmprestimo : emprestimoListOld) {
                if (!emprestimoListNew.contains(emprestimoListOldEmprestimo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Emprestimo " + emprestimoListOldEmprestimo + " since its IDUsuario field is not nullable.");
                }
            }
            for (ListaEspera listaEsperaListOldListaEspera : listaEsperaListOld) {
                if (!listaEsperaListNew.contains(listaEsperaListOldListaEspera)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ListaEspera " + listaEsperaListOldListaEspera + " since its IDUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Emprestimo> attachedEmprestimoListNew = new ArrayList<Emprestimo>();
            for (Emprestimo emprestimoListNewEmprestimoToAttach : emprestimoListNew) {
                emprestimoListNewEmprestimoToAttach = em.getReference(emprestimoListNewEmprestimoToAttach.getClass(), emprestimoListNewEmprestimoToAttach.getIDEmprestimo());
                attachedEmprestimoListNew.add(emprestimoListNewEmprestimoToAttach);
            }
            emprestimoListNew = attachedEmprestimoListNew;
            usuario.setEmprestimoList(emprestimoListNew);
            List<ListaEspera> attachedListaEsperaListNew = new ArrayList<ListaEspera>();
            for (ListaEspera listaEsperaListNewListaEsperaToAttach : listaEsperaListNew) {
                listaEsperaListNewListaEsperaToAttach = em.getReference(listaEsperaListNewListaEsperaToAttach.getClass(), listaEsperaListNewListaEsperaToAttach.getIDLista());
                attachedListaEsperaListNew.add(listaEsperaListNewListaEsperaToAttach);
            }
            listaEsperaListNew = attachedListaEsperaListNew;
            usuario.setListaEsperaList(listaEsperaListNew);
            usuario = em.merge(usuario);
            for (Emprestimo emprestimoListNewEmprestimo : emprestimoListNew) {
                if (!emprestimoListOld.contains(emprestimoListNewEmprestimo)) {
                    Usuario oldIDUsuarioOfEmprestimoListNewEmprestimo = emprestimoListNewEmprestimo.getIDUsuario();
                    emprestimoListNewEmprestimo.setIDUsuario(usuario);
                    emprestimoListNewEmprestimo = em.merge(emprestimoListNewEmprestimo);
                    if (oldIDUsuarioOfEmprestimoListNewEmprestimo != null && !oldIDUsuarioOfEmprestimoListNewEmprestimo.equals(usuario)) {
                        oldIDUsuarioOfEmprestimoListNewEmprestimo.getEmprestimoList().remove(emprestimoListNewEmprestimo);
                        oldIDUsuarioOfEmprestimoListNewEmprestimo = em.merge(oldIDUsuarioOfEmprestimoListNewEmprestimo);
                    }
                }
            }
            for (ListaEspera listaEsperaListNewListaEspera : listaEsperaListNew) {
                if (!listaEsperaListOld.contains(listaEsperaListNewListaEspera)) {
                    Usuario oldIDUsuarioOfListaEsperaListNewListaEspera = listaEsperaListNewListaEspera.getIDUsuario();
                    listaEsperaListNewListaEspera.setIDUsuario(usuario);
                    listaEsperaListNewListaEspera = em.merge(listaEsperaListNewListaEspera);
                    if (oldIDUsuarioOfListaEsperaListNewListaEspera != null && !oldIDUsuarioOfListaEsperaListNewListaEspera.equals(usuario)) {
                        oldIDUsuarioOfListaEsperaListNewListaEspera.getListaEsperaList().remove(listaEsperaListNewListaEspera);
                        oldIDUsuarioOfListaEsperaListNewListaEspera = em.merge(oldIDUsuarioOfListaEsperaListNewListaEspera);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getIDUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIDUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Emprestimo> emprestimoListOrphanCheck = usuario.getEmprestimoList();
            for (Emprestimo emprestimoListOrphanCheckEmprestimo : emprestimoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Emprestimo " + emprestimoListOrphanCheckEmprestimo + " in its emprestimoList field has a non-nullable IDUsuario field.");
            }
            List<ListaEspera> listaEsperaListOrphanCheck = usuario.getListaEsperaList();
            for (ListaEspera listaEsperaListOrphanCheckListaEspera : listaEsperaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the ListaEspera " + listaEsperaListOrphanCheckListaEspera + " in its listaEsperaList field has a non-nullable IDUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
