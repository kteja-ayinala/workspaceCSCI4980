/*
 * @(#) GClassNode.java
 *
 */
package graph.model.node;

import graph.model.GNode;

public class GClassNode extends GNode {

	public GClassNode(String id, String name, boolean isPublic) {
		super(id, name);
	}

	public boolean isPublic() {
		return true;
	}

}
