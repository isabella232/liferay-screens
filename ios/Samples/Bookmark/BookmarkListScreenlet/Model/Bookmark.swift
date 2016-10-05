/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
import UIKit

@objc public class Bookmark : NSObject {
    
    public let attributes: [String:AnyObject]
    
    public var name: String {
        return attributes["name"] as! String
    }
    
    override public var description: String {
        return attributes["description"] as! String
    }
    
    public var url: String {
        return attributes["url"] as! String
    }
    
    public init(attributes: [String:AnyObject]) {
        self.attributes = attributes
    }
    
}
