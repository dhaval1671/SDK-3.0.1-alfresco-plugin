package gov.uspto.trademark.cms.repo.services;

/**
 * This interface is used to perform post create operations.
 * 
 * @author vnondapaka
 *
 */
public interface DocumentCreateListener {
    /**
     * Implement the functionality that needs to be implemented after creating a
     * document.
     */
    void listen();
}