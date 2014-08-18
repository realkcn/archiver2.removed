package org.kbs.archiver.repositories;

import java.io.InputStream;

/**
 * Created by kcn on 14-8-13.
 */

public interface AttachmentDAO {
    InputStream get(String id);

    void put(String id, InputStream data);

    String put(InputStream data);

    void delete(String id);

    void clear();
}
