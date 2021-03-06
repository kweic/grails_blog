package kw_blog.com.manifestcorp

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class User implements Serializable, Comparable<User> {

	private static final long serialVersionUID = 1

	transient springSecurityService

	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	SortedSet blogs

	static hasMany = [blogs:Blog]

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this)*.role
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	def addBlog(blog){
		blogs.add(blog)
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

	static transients = ['springSecurityService']

	static constraints = {
		password blank: false, password: true
		username blank: false, unique: true
	}

	static mapping = {
		password column: '`password`'
	}

	@Override
	int compareTo(User o) {
		if(!blogs.isEmpty() && !o.blogs.isEmpty()) {
			return ((Blog) o.blogs.first()).dateCreated.compareTo(((Blog) blogs.first()).dateCreated)
		}else if(!blogs.isEmpty()){
			return -1;
		}else if(!o.blogs.isEmpty()){
			return 1;
		}

		return this.id - o.id;

	}
}
