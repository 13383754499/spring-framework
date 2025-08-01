/*
 * Copyright 2002-present the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.docs.dataaccess.jdbc.jdbccomplextypes;

import java.sql.CallableStatement;
import java.sql.Struct;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

@SuppressWarnings("unused")
public class TestItemStoredProcedure extends StoredProcedure {

	public TestItemStoredProcedure(DataSource dataSource) {
		super(dataSource, "get_item");
		declareParameter(new SqlOutParameter("item", Types.STRUCT, "ITEM_TYPE",
				(CallableStatement cs, int colIndx, int sqlType, String typeName) -> {
					Struct struct = (Struct) cs.getObject(colIndx);
					Object[] attr = struct.getAttributes();
					TestItem item = new TestItem();
					item.setId(((Number) attr[0]).longValue());
					item.setDescription((String) attr[1]);
					item.setExpirationDate((java.util.Date) attr[2]);
					return item;
				}));
		// ...
	}

}