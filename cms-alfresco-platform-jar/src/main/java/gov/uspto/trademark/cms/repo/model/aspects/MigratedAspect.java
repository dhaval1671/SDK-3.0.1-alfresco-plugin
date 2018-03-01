package gov.uspto.trademark.cms.repo.model.aspects;

/**
 * Migration aspect representation in the repository
 *
 * Created by bgummadi on 6/2/2014.
 */
public class MigratedAspect extends AbstractBaseAspect {

    /** The migration method. */
    private String migrationMethod;

    /** The migration source. */
    private String migrationSource;

    /**
     * Gets the migration method.
     *
     * @return the migration method
     */
    public String getMigrationMethod() {
        return migrationMethod;
    }

    /**
     * Sets the migration method.
     *
     * @param migrationMethod
     *            the new migration method
     */
    public void setMigrationMethod(String migrationMethod) {
        this.migrationMethod = migrationMethod;
    }

    /**
     * Gets the migration source.
     *
     * @return the migration source
     */
    public String getMigrationSource() {
        return migrationSource;
    }

    /**
     * Sets the migration source.
     *
     * @param migrationSource
     *            the new migration source
     */
    public void setMigrationSource(String migrationSource) {
        this.migrationSource = migrationSource;
    }

}