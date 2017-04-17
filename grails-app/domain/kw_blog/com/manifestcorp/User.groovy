package kw_blog.com.manifestcorp

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class User implements Serializable{

	private static final long serialVersionUID = 1

	transient springSecurityService

	Long id
	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	Date lastActiveDate
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

	def updateLastActive(){
		lastActiveDate = new Date()
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

	static transients = ['springSecurityService']

	static constraints = {
		password blank: false, password: true
		username blank: false, unique: true
		lastActiveDate (nullable: true)
	}

	static mapping = {
		password column: '`password`'
	}

	static List<User> orderByBlogCount(int max, int offset) {
		return User.executeQuery("""
        	SELECT u
        	FROM User u
        	ORDER BY size(u.blogs) DESC
    		""", [max: max, offset: offset])
	}
}
