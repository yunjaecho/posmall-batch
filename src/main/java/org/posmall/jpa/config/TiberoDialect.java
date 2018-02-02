package org.posmall.jpa.config;

import org.hibernate.dialect.Oracle10gDialect;

/**
 * Created by USER on 2018-02-02.
 */
public class TiberoDialect extends Oracle10gDialect {
    @Override
    public String getQuerySequencesString() {
        return " select sequence_name from all_sequences";
    }
}
