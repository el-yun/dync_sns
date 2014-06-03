package dync.db;

import java.sql.SQLException;

import dync.model.Code_Repository;

public class RepositoryPersistentManager extends ConnectDB {
	public boolean insertRepository(Code_Repository repository) {
		connect();
		String sql = "insert into CODE_REPOSITORY (CODE_REPOSITORY,USER_ID)"
				+ "values(?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, repository.getCode_repository());
			pstmt.setInt(2, repository.getUser_id());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(pstmt.toString());
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;
	}

	public boolean deleteRepository(int code_repository) {
		connect();

		String sql = "delete from CODE_REPOSITORY where CODE_REPOSITORY=?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, code_repository);
			pstmt.executeUpdate();

			System.out.println(pstmt.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			disconnect();
		}
		return true;
	}
}
