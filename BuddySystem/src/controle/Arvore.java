/*
 * Guarda arvore de nos para estrutura
 */
package controle;

public class Arvore {
    private No root;
}

/** Exemplo de codigo de arvore; disponivel em: https://github.com/utkarshsimha/buddy_system/blob/master/src/Tree.java
import java.util.ArrayList;
public class Tree
{
	private Node root;
	private int root_size;
	private int root_x,root_y;
	private int index = 0;
	private int diff = 150;
	public ArrayList<Node> list = new ArrayList<Node>();
	public Tree( int size, int x, int y ) throws NotPowerOfTwoException
	{
		root = null;
		if( size == 1 || ( (size & ( size - 1 )) != 0 ) )
		{
			throw new NotPowerOfTwoException("Input not a power of two");
		}
		else
		{
			// System.out.println("Creating node of init size");
			root_size = size;
			root_x = x;
			root_y = y;
			root = createNode( null, root_size, root_x, root_y );
		}
			
	}
	
	private Node createNode( Node parent, int node_size, int node_x, int node_y )
	{
		Node n = new Node( parent, node_size, node_x, node_y );
		list.add(index,n);
		index++;
		return n;
	}
	
	public void requestMem( int in_size ) throws NotEnoughMemoryException
	{
		if( in_size > root_size )
		{
			throw new NotEnoughMemoryException("Request exceeded initial size");
		}
		Node n = findNode( root, in_size );
		if( n == null )
		{
			throw new NotEnoughMemoryException("Requested size : "+in_size+"\nNot enough memory! Reclaim some memory first.");
		}
		n.allocateMem( in_size );
	}
	
	private Node findNode( Node n, int size )
	{
		if( n.used == true )
		{
			// System.out.println("Returning from is used block");
			return null;
		}
		System.out.println("Find node called with node size "+n.size+" in_size "+size);
		Node temp = null;
		if( !isSplit( n ) && size <= n.size && size > n.size/2 )
		{
			if( n.used == false )
			{
				// System.out.println("Found node, returning n");
				return n;
			}
			else
			{
				// System.out.println("Returning from if block"+" In size : "+size+" Node size : "+n.size);
				return null;
			}
		}
		else if( size <= n.size/2 )
		{
			if( !isSplit( n ) )
			{
				splitNode( n );
			}
			temp = findNode( n.left, size );
			if( temp != null )
			{
				// System.out.println("Returning temp from left\n");
				return temp;
			}
			temp = findNode( n.right, size );
			if( temp!= null )
			{
				// System.out.println("Returning temp from right");
				return temp;
			}
			else
			{
				// System.out.println("Returning from else-if block"+" In size : "+size+" Node size : "+n.size);
				return null;
			}
		}
		else
		{
			// System.out.println("Returning from else block"+" In size : "+size+" Node size : "+n.size);
			return null;
		}
	}
	
	private void splitNode( Node n )
	{
		n.left = createNode( n, n.size/2, n.x-diff, n.y+diff );
		n.right = createNode( n, n.size/2, n.x+diff, n.y+diff );
		diff-=20;
	}
	
	private boolean isSplit( Node n )
	{
		if( n.left == null || n.right == null )
			return false;
		else
			return true;
	}
}
 */
