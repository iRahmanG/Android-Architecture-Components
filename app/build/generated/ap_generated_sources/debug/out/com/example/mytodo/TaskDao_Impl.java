package com.example.mytodo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeleteOrUpdateAdapter;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class TaskDao_Impl implements TaskDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<Task> __insertAdapterOfTask;

  private final EntityDeleteOrUpdateAdapter<Task> __deleteAdapterOfTask;

  private final EntityDeleteOrUpdateAdapter<Task> __updateAdapterOfTask;

  public TaskDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfTask = new EntityInsertAdapter<Task>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `task_table` (`id`,`title`,`isCompleted`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Task entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getTitle());
        }
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(3, _tmp);
      }
    };
    this.__deleteAdapterOfTask = new EntityDeleteOrUpdateAdapter<Task>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `task_table` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Task entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTask = new EntityDeleteOrUpdateAdapter<Task>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `task_table` SET `id` = ?,`title` = ?,`isCompleted` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement, final Task entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getTitle());
        }
        final int _tmp = entity.isCompleted() ? 1 : 0;
        statement.bindLong(3, _tmp);
        statement.bindLong(4, entity.getId());
      }
    };
  }

  @Override
  public void insert(final Task task) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __insertAdapterOfTask.insert(_connection, task);
      return null;
    });
  }

  @Override
  public void delete(final Task task) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __deleteAdapterOfTask.handle(_connection, task);
      return null;
    });
  }

  @Override
  public void update(final Task task) {
    DBUtil.performBlocking(__db, false, true, (_connection) -> {
      __updateAdapterOfTask.handle(_connection, task);
      return null;
    });
  }

  @Override
  public LiveData<List<Task>> getAllTasks() {
    final String _sql = "SELECT * FROM task_table";
    return __db.getInvalidationTracker().createLiveData(new String[] {"task_table"}, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfTitle = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "title");
        final int _columnIndexOfIsCompleted = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "isCompleted");
        final List<Task> _result = new ArrayList<Task>();
        while (_stmt.step()) {
          final Task _item;
          _item = new Task();
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          _item.setId(_tmpId);
          final String _tmpTitle;
          if (_stmt.isNull(_columnIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = _stmt.getText(_columnIndexOfTitle);
          }
          _item.setTitle(_tmpTitle);
          final boolean _tmpIsCompleted;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfIsCompleted));
          _tmpIsCompleted = _tmp != 0;
          _item.setCompleted(_tmpIsCompleted);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
